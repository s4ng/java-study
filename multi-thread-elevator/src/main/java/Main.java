import lombok.*;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {

	static List< String > logs = new ArrayList<>();
	static Queue< Waiting > waitingQueue;

	static Elevator e1 = new Elevator( null, 0, 1, 0, false );
	static Elevator e2 = new Elevator( null, 0, 1, 0, false );
	static Elevator e3 = new Elevator( null, 0, 1, 0, false );

	public static void main( String[] args ) throws InterruptedException {

		createWaitingQueue();

		BlockingQueue< Runnable > blockingQueue = new ArrayBlockingQueue<>( 10 );
		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor( 3, 3, 0, TimeUnit.SECONDS, blockingQueue );

		while ( !waitingQueue.isEmpty() ) {
			threadPoolExecutor.execute( new Task( waitingQueue.poll() ) );
		}

		while ( true ) {
			printDashBoard( e1, e2, e3 );
			Thread.sleep( 500L );
		}
	}

	@Getter
	@Setter
	@AllArgsConstructor
	public static class Elevator {
		Boolean isUp;
		int peopleCount;
		int flour;
		int weight;
		boolean isMoving;

		public String getIsUpToString() {
			if ( isUp == null ) {
				return "STOP";
			} else if ( isUp ) {
				return "UP  ";
			} else {
				return "DOWN";
			}
		}

		public void addPeople( int peopleWeight ) {
			this.peopleCount++;
			this.weight += peopleWeight;
		}

		public void pollPeople( int weight ) {
			this.peopleCount--;
			this.weight -= weight;
		}
	}

	@AllArgsConstructor
	static class Task implements Runnable {

		private final Waiting waiting;

		@SneakyThrows
		@Override
		public synchronized void run() {
			Elevator elevator = chooseElevator( waiting );

			while ( elevator == null ) {
				Thread.sleep( 1000L );
				elevator = chooseElevator( waiting );
			}
			elevator.setMoving( true );
			while ( waiting.getNowFlour() != elevator.getFlour() ) {
				Thread.sleep( 1000L );
				if ( waiting.getNowFlour() > elevator.getFlour() ) {
					elevator.setIsUp( true );
					elevator.setFlour( elevator.getFlour() + 1 );
				} else {
					elevator.setIsUp( false );
					elevator.setFlour( elevator.getFlour() - 1 );
				}
			}
			logs.add( waiting.nowFlour + "층에서 " + 1 + "명 탑승함." );
			Thread.sleep( 1000L );
			elevator.setIsUp( null );
			elevator.addPeople( waiting.weight );
			while ( waiting.getGoingFlour() != elevator.getFlour() ) {
				Thread.sleep( 1000L );
				if ( waiting.getGoingFlour() > elevator.getFlour() ) {
					elevator.setIsUp( true );
					elevator.setFlour( elevator.getFlour() + 1 );
				} else {
					elevator.setIsUp( false );
					elevator.setFlour( elevator.getFlour() - 1 );
				}
			}
			Thread.sleep( 1000L );
			elevator.setIsUp( null );
			elevator.pollPeople( waiting.weight );
			elevator.setMoving( false );
			logs.add( waiting.goingFlour + "층에서 " + 1 + "명 내림." );
		}
	}

	@Getter
	@Builder
	@AllArgsConstructor
	static class Waiting {

		private int nowFlour;
		private int goingFlour;
		private int weight;
	}

	private static void createWaitingQueue() {
		Queue< Waiting > waitingQueueTemp = new LinkedList<>();
		waitingQueueTemp.add( Waiting.builder().nowFlour( 3 ).goingFlour( 5 ).weight( 50 ).build() );
		waitingQueueTemp.add( Waiting.builder().nowFlour( 2 ).goingFlour( 4 ).weight( 70 ).build() );
		waitingQueueTemp.add( Waiting.builder().nowFlour( 4 ).goingFlour( 7 ).weight( 60 ).build() );
		waitingQueueTemp.add( Waiting.builder().nowFlour( 6 ).goingFlour( 8 ).weight( 70 ).build() );
		waitingQueueTemp.add( Waiting.builder().nowFlour( 4 ).goingFlour( 1 ).weight( 60 ).build() );
		waitingQueueTemp.add( Waiting.builder().nowFlour( 3 ).goingFlour( 7 ).weight( 70 ).build() );
		waitingQueueTemp.add( Waiting.builder().nowFlour( 6 ).goingFlour( 4 ).weight( 80 ).build() );
		waitingQueueTemp.add( Waiting.builder().nowFlour( 5 ).goingFlour( 6 ).weight( 60 ).build() );
		waitingQueueTemp.add( Waiting.builder().nowFlour( 10 ).goingFlour( 1 ).weight( 50 ).build() );
		waitingQueueTemp.add( Waiting.builder().nowFlour( 2 ).goingFlour( 3 ).weight( 46 ).build() );
		waitingQueue = waitingQueueTemp;
	}

	private static Elevator chooseElevator( Waiting waiting ) {

		int e1Dis = e1.isMoving() ? Integer.MAX_VALUE : e1.flour - Math.abs( waiting.getNowFlour() );
		int e2Dis = e2.isMoving() ? Integer.MAX_VALUE : e2.flour - Math.abs( waiting.getNowFlour() );
		int e3Dis = e3.isMoving() ? Integer.MAX_VALUE : e3.flour - Math.abs( waiting.getNowFlour() );
		if ( e1Dis == Integer.MAX_VALUE && e2Dis == Integer.MAX_VALUE && e3Dis == Integer.MAX_VALUE ) {
			return null;
		} else {
			if ( e1Dis < e2Dis ) {
				if ( e1Dis < e3Dis ) {
					return e1;
				} else {
					return e3;
				}
			} else if ( e2Dis < e3Dis ) {
				return e2;
			} else {
				return e3;
			}
		}
	}

	private static void printDashBoard( Elevator e1, Elevator e2, Elevator e3 ) {
		int one, two, three;
		clearScreen();
		printLine();
		for ( int i = 10; i >= 1; i-- ) {
			System.out.println( i + ( i == 10 ? "층" : " 층" ) + "  |    " + printElevator( e1, i ) +
					printElevator( e2, i ) + printElevator( e3, i ) );
		}
		printLine();
		System.out.println( "[엘리베이터]" );
		System.out.println( "- 1호기 : 현재 위치 : " + ( e1.flour == 10 ? "10층" : " " + e1.flour + "층" )
				+ " / " + "방향 : " + e1.getIsUpToString() + " / 적재량 : " + e1.getWeight() + " / 인원수 : " + e1.getPeopleCount() );
		System.out.println( "- 2호기 : 현재 위치 : " + ( e2.flour == 10 ? "10층" : " " + e2.flour + "층" )
				+ " / " + "방향 : " + e2.getIsUpToString() + " / 적재량 : " + e2.getWeight() + " / 인원수 : " + e2.getPeopleCount() );
		System.out.println( "- 3호기 : 현재 위치 : " + ( e3.flour == 10 ? "10층" : " " + e3.flour + "층" )
				+ " / " + "방향 : " + e3.getIsUpToString() + " / 적재량 : " + e3.getWeight() + " / 인원수 : " + e3.getPeopleCount() );

		System.out.println( "[최근 이벤트]" );
		if ( logs.size() > 0 ) {
			System.out.println( logs.get( logs.size() - 1 ) );
		}
	}

	private static String printElevator( Elevator elevator, int flour ) {
		StringBuilder sb = new StringBuilder();
		if ( elevator.getFlour() != flour ) {
			sb.append( " [                 ] " );
		} else {
			sb.append( " [ " );
			sb.append( elevator.getIsUp() == null ? "ST :  " : ( elevator.getIsUp() ? "UP : " : "DN :  " ) );
			sb.append( elevator.getWeight() ).append( "/1000" );
			if ( sb.length() <= 19 ) {
				sb.append( " ".repeat( Math.max( 0, 19 - sb.length() ) ) );
			}
			sb.append( "] " );
		}

		return sb.toString();
	}

	private static void printLine() {
		System.out.println( "-------------------------------------------------------------------------" );
	}

	public static void clearScreen() {
//		System.out.println( new String( new char[ 30 ] ).replace( "\0", "\r\n" ) );
		for ( int i = 0; i < 20; i++ ) {
			System.out.println( "\b" );
		}
	}
}
