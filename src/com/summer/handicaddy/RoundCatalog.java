package com.summer.handicaddy;

import java.io.Serializable;
import java.util.*;

public class RoundCatalog implements Serializable
{

	private static final long serialVersionUID = 2L;

	private class Node implements Serializable{
		private static final long serialVersionUID = 7275432282107618077L;
		private Node next; //points to node with next chronological round
		private Node last; //points to node with last chronological round
		private Round round;
		private Node lastPlayCourse; //points to the last round at given course
		private Node lastPlayTeeBox; //points to the last round at given tee box
		public int day; // denotes the day of this node
		
		/**
		 * Connect this to node, ie this.next = node and node.last = this
		 * @param node
		 */
		private void connect(Node node)
		{
			this.next = node;
			node.last = this;
		}
		
	}

	ArrayList<Node> days; //provides map from day to node
	HashMap<Course, Node> courses; //provides map from course to network of nodes at given course
	HashMap<TeeBox, Node> teeBoxes; //provides map from teeBox to network of nodes at given teeBox
	HashMap<String, Course> stringToCourse; //gives map from string to course

	private int day1;
	private Node end;
	
	public RoundCatalog(int day1, int numDays)
	{

		end = new Node();
		end.day = -1;
		end.next = end;
		end.last = end;
		
		stringToCourse = new HashMap<String, Course>();
		
		this.day1 = day1;

		days = new ArrayList<Node>();
		Node temp;

		for (int i = 0; i < numDays; i++)
		{
			temp = new Node();
			temp.last = end;
			temp.next = end;
			temp.day  = i;
			days.add(temp);
		}

		
		courses = new HashMap<Course, Node>();
		
		teeBoxes = new HashMap<TeeBox, Node>();

	}
	
	public void addRound(Round round)
	{
		
		int day = round.getDay(); //day on which round was played
		
		if(!(days.size() > day - day1)) //add new days if needed
			extendDays(day);
		
		Node today = days.get(day - day1); //add node at (day - day1) in Arraylist
		
		Course course = round.getCourse();
		
		TeeBox teeBox = round.getTeeBox();
		
		stringToCourse.put(course.getName(), course);
		
		Node temp;
		
		if(today.round == null)
		{
			today.round = round;

			if(!courses.containsKey(course))
				today.lastPlayCourse = end;
			else
				today.lastPlayCourse = courses.get(course);
			
			if(!teeBoxes.containsKey(teeBox))
				today.lastPlayTeeBox = end;
			else
				today.lastPlayTeeBox = teeBoxes.get(teeBox);


			int lastPlay = today.last.day;

			if(lastPlay == -1)
				closeLeft(lastPlay, today);
			else
			{
				temp = days.get(lastPlay);
				days.set(lastPlay, today.last);
				closeLeft(lastPlay, today);
				days.set(lastPlay, temp);
			}

			closeRight(today.next.day, today);
			courses.put(course, today);
			teeBoxes.put(teeBox, today);
			//courseNames.add(course.getName());
		}

		else
		{
			Node newToday = new Node();
			newToday.round = round;
			newToday.day = day;

			temp = today.next;

			today.next = newToday;
			newToday.last = today;
			newToday.next = temp;

			days.set(day - day1, newToday); //point arraylist to last round of day
			closeRight(temp.day, newToday);
			days.set(day - day1, today); //re-point arraylist to first round of day

			if(!courses.containsKey(course)) 
				newToday.lastPlayCourse = end;
			else
				newToday.lastPlayCourse = courses.get(course);

			if(!teeBoxes.containsKey(course)) 
				newToday.lastPlayTeeBox = end;
			else
				newToday.lastPlayTeeBox = courses.get(course);

			courses.put(course, newToday);
			teeBoxes.put(teeBox, newToday);

		}

	}

	private void closeRight(int endDay, Node target) //endDay is index of next round already networked
	{

		int startDay = target.day;
		Node temp = target;

		if(endDay == -1)
			endDay = days.size() - 1;

		while(temp.day != endDay)
		{
			temp = days.get(startDay++);
			temp.last = target;
		}

	}

	private void closeLeft(int startDay, Node target)//startDay is index of last round already networked
	{

		int endDay = target.day;
		Node temp;

		if (startDay != -1)
			temp = days.get(startDay);
		else
			temp = end;

		while(temp.day != endDay)
		{
			temp.next = target;
			startDay++;
			temp = days.get(startDay);
		}

	}
	
	private void extendDays(int day) //extends the length of the 'days' arraylist
	{
		
		Node lastRound = days.get(days.size() - day1).last;

		Node temp = new Node();
		
		for(int i = days.size() + day1; i < day; i++)
		{
			temp = new Node();
			temp.last = lastRound;
			temp.next = end;
			temp.day = i;
			days.add(temp);
		}
		
	}

	public Round[] getRoundsByDate(int start, int stop) //rounds with days in [start, stop]
	{
		
		ArrayList<Round> rounds = new ArrayList<Round>();

		Node tracker = days.get(start);
		if (tracker.round != null)
			rounds.add(tracker.round);

		tracker = tracker.next;
		while (tracker != end && tracker.day <= stop)
		{
			rounds.add(tracker.round);
			tracker = tracker.next;
		}
		
		Round[] roundArray = new Round[rounds.size()];
		return rounds.toArray(roundArray);
	}
	
	public Round[] getAllRounds() { return getRoundsByDate(0, days.size() - 1); }
	
	public int numRounds() { return getAllRounds().length; 
		/*int totalRounds = 0;
		for (int i = 0; i < days.size(); i++) {
			if (days.get(i).round !=null) 
				totalRounds++;
			
		}*/
	}
	
	/**
	 * Get an array of rounds played at a given course
	 * @param course
	 * @return
	 */
	public Round[] getRounds(Course course) //returns rounds played at given course, most recent in lower indices
	{
		ArrayList<Round> rounds = new ArrayList<Round>();

		Node tracker = courses.get(course);

		if(tracker == null)
			return (Round[]) rounds.toArray();

		while(tracker != end)
		{
			rounds.add(tracker.round);
			tracker = tracker.lastPlayCourse;
		}

		Round[] roundArray = new Round[rounds.size()];
		return rounds.toArray(roundArray);
	}
	
	/**
	 * Get rounds played at course between start and end date
	 * @param course
	 * @param start
	 * @param end
	 * @return
	 */
	public Round[] getRounds(Course course, Date start, Date end)
	{
		ArrayList<Round> roundsInDates = new ArrayList<Round>();
		
		
		Round[] allRounds = getRounds(course);
		
		
		Round tracker; //temporary round pointer
		Date temp; //temporary date pointer
		
		
		for(int i = 0; i < allRounds.length; i++)
		{
			tracker = roundsInDates.get(i);
			temp = tracker.getDate();
			
			/*if indicator >= 2, then the date is out of range because 
			 *it is before or after both dates and hence sums to +/- 2 */
			int indicator = Math.abs(temp.compareDate(start) + temp.compareDate(end));
			
			if(indicator < 2) { roundsInDates.add(tracker); }
		}
		
		Round[] roundArray = new Round[roundsInDates.size()];
		return roundsInDates.toArray(roundArray);
		
	}
	
	/**
	 * Get an array of rounds played at a given tee box
	 * @param teeBox
	 * @return
	 */
	public Round[] getRounds(TeeBox teeBox) //returns rounds played at given teeBox, most recent in lower indices
	{
		ArrayList<Round> rounds = new ArrayList<Round>();

		Node tracker = teeBoxes.get(teeBox);

		if(tracker == null)
			return (Round[]) rounds.toArray();

		while(tracker != end)
		{
			rounds.add(tracker.round);
			tracker = tracker.lastPlayTeeBox;
		}

		Round[] roundArray = new Round[rounds.size()];
		return rounds.toArray(roundArray);
	}
	
	/**
	 * Get an array of rounds played at a given tee boxes
	 * @param teeBox
	 * @return
	 */
	public Round[] getRounds(TeeBox[] boxes) //returns rounds played at given course, most recent in lower indices
	{
		ArrayList<Round> rounds = new ArrayList<Round>();

		Node[] trackers = new Node[boxes.length];
		
		for(int i = 0; i < trackers.length; i++)
			trackers[i] = teeBoxes.get(boxes[i]);

		if(trackers[0] == null)
			return (Round[]) rounds.toArray();

		for(int i = 0; i < trackers.length; i++)
		{
			while(trackers[i] != end)
			{
				rounds.add(trackers[i].round);
				trackers[i] = trackers[i].lastPlayTeeBox;
			}
		}

		Round[] roundArray = new Round[rounds.size()];
		return rounds.toArray(roundArray);
	}
	
	/**
	 * Get rounds played at teeBox between start and end date
	 * @param teeBox
	 * @param start
	 * @param end
	 * @return
	 */
	public Round[] getRounds(TeeBox teeBox, Date start, Date end)
	{
		//need to fix this conditional
		if(start == null || end == null)
			return getRounds(teeBox);
		
		ArrayList<Round> roundsInDates = new ArrayList<Round>();
		
		
		Round[] allRounds = getRounds(teeBox);
		
		
		Round tracker; //temporary round pointer
		Date temp; //temporary date pointer
		
		
		for(int i = 0; i < allRounds.length; i++)
		{
			tracker = roundsInDates.get(i);
			temp = tracker.getDate();
			
			/*if indicator >= 2, then the date is out of range because 
			 *it is before or after both dates and hence sums to +/- 2 */
			int indicator = Math.abs(temp.compareDate(start) + temp.compareDate(end));
			
			if(indicator < 2) { roundsInDates.add(tracker); }
		}
		
		Round[] roundArray = new Round[roundsInDates.size()];
		return roundsInDates.toArray(roundArray);
		
	}
	
	/**
	 * Get rounds played at teeBoxes between start and end date
	 * @param teeBox
	 * @param start
	 * @param end
	 * @return
	 */
	public Round[] getRounds(TeeBox[] boxes, Date start, Date end)
	{
		//need to fix this conditional
		if(start == null || end == null)
			return getRounds(boxes);
		
		ArrayList<Round> roundsInDates = new ArrayList<Round>();
		
		
		Round[] allRounds = getRounds(boxes);
		
		
		Round tracker; //temporary round pointer
		Date temp; //temporary date pointer
		
		
		for(int i = 0; i < allRounds.length; i++)
		{
			tracker = roundsInDates.get(i);
			temp = tracker.getDate();
			
			/*if indicator >= 2, then the date is out of range because 
			 *it is before or after both dates and hence sums to +/- 2 */
			int indicator = Math.abs(temp.compareDate(start) + temp.compareDate(end));
			
			if(indicator < 2) { roundsInDates.add(tracker); }
		}
		
		Round[] roundArray = new Round[roundsInDates.size()];
		return roundsInDates.toArray(roundArray);
		
	}
	
	public boolean coursePlayed(Course input) { return courses.containsKey(input); }
	
	public Course getCourseIfPlayed(String course) { return stringToCourse.get(course); }

	public static void main(String[] args)
	{
		/*
		int days = 1000;
		RoundCatalog catalog = new RoundCatalog(0, days); //days range from 0 to (days - 1)
		
		Course hoodfield = new Course("Hoodfield C.C.", 72);
		Course mizner    = new Course("Mizner C.C.", 70);
		Course bMuni     = new Course("Boca Municipal G.C.", 72);
		Course augusta   = new Course("Augusta National", 72);
		Course atlantic  = new Course("Atlantic Avenue", 70);

		Round one   = new Round(hoodfield, "one");
		Round two   = new Round(mizner, "two");
		Round three = new Round(bMuni, "three");
		Round four  = new Round(hoodfield, "four");
		Round five  = new Round(mizner, "five");
		Round six   = new Round(bMuni, "six");
		Round seven = new Round(augusta, "seven");
		Round eight = new Round(atlantic, "eight");
		Round nine  = new Round(bMuni, "nine");

		catalog.addRound(0, one);//one five four four two three nine three six seven three four five two
		catalog.addRound(0, five);
		catalog.addRound(2, one);
		catalog.addRound(77, one);
		//		catalog.addRound(4, three);
		//		catalog.addRound(999, four);
		//		catalog.addRound(1, four);
		//		catalog.addRound(77, seven);
		//		catalog.addRound(999, nine);
		//		catalog.addRound(34, three);
		//		catalog.addRound(79, five);
		//		catalog.addRound(999, eight);
		//		catalog.addRound(78, four);
		//		catalog.addRound(0, three);

		//        catalog.addRound(0, one);
		//        catalog.addRound(0, two);
		//        catalog.addRound(2, three);
		//        catalog.addRound(2, six);
		//        catalog.addRound(3, four);
		//        catalog.addRound(4, five);

		System.out.println(catalog.days.get(0).next.round.getCourse().getName());

		Round[] rounds = catalog.getRoundsByDate(0, days);

		Round temp;
		for(int i = 0; i < rounds.length; i++)
		{
			temp = (Round) rounds[i];
			System.out.println(temp.getScore() + " " + temp.getCourse().getName());
		}

		rounds = catalog.getRoundsByCourse(bMuni);
		for(int i = 0; i < rounds.length; i++)
		{
			temp = rounds[i];
			System.out.println(temp.getScore() + " at " + temp.getCourse().getName());
		}

		Iterator<Course> coursesPlayed = catalog.courses.keySet().iterator();
		while (coursesPlayed.hasNext()) {
			Course temporary = coursesPlayed.next();
			System.out.println("Last play at " + temporary.getName() + " shot " + catalog.courses.get(temporary).round.getScore());
		}
		*/
	}

	public String toString()
	{
		Node temp = days.get(0);

		String s = "";

		if(temp.round == null)
			temp = temp.next;

		while(temp!=end)
		{
			s = s + temp.round.getScore() + " ";
			temp = temp.next;
		}

		return s;

	}
	
	public String[] getCourseNames(){
		String[] s = new String[stringToCourse.keySet().size()];
		return (String[]) stringToCourse.keySet().toArray(s);
	}
	
	public Course[] getCourses(){
		Set<Course> s = courses.keySet();
		Course[] c = new Course[s.size()];
		return s.toArray(c);
	}
	
	public TeeBox[] getTeeBoxes(){
		Set<TeeBox> s = teeBoxes.keySet();
		TeeBox[] t = new TeeBox[s.size()];
		return s.toArray(t);
	}
	
	
	
}