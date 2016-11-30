import java.util.Set;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Problem 
{
	public Problem(String polygon)
	{
		System.out.println("Polygone " + polygon);
		triangulate(process(polygon), new HashSet<Segment>());
	}
	
	private Set<Character> process(String polygonStr){
		return polygonStr.chars().mapToObj(e->(char)e).collect(Collectors.toSet());
	}

	private Set<Set<Segment>> triangulate(Set<Character> polygon, Set<Segment> alreadyUsed){
		
		
		
		return new HashSet<Set<Segment>>();
	}
	
	private class Segment{
		public char firstPoint;
		public char secondPoint;
		public Segment(char firstPoint, char secondPoint){
			this.firstPoint = firstPoint;
			this.secondPoint = secondPoint;
		}
	}
}