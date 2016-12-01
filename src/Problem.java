import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Problem 
{
	public Problem(String polygon)
	{
		System.out.println("Polygone " + polygon);
		display(triangulate(process(polygon), new HashSet<Set<Character>>()));
	}
	
	private List<Character> process(String polygonStr){
		return polygonStr.chars().mapToObj(e->(char)e).collect(Collectors.toList());
	}
	
    private void display(List<Set<Segment>> solutions){
    	for (int i = 0; i < solutions.size(); i++){
        	Set<Segment> solution = solutions.get(i);
        	System.out.print((i+1) + ": ");
            for (Segment segment : solution){
                System.out.print("" + segment.firstVertex + segment.secondVertex + " ");
            }
            System.out.println();
        }
    }

	private List<Set<Segment>> triangulate(List<Character> polygon, Set<Set<Character>> alreadyUsed){
		List<Set<Segment>> result = new ArrayList<Set<Segment>>();
		Set<Set<Character>> alreadyUsedCopy = new HashSet<Set<Character>>(alreadyUsed); 
		if (polygon.size() <= 3)
			return new ArrayList<Set<Segment>>();
		for (int i = 3; i < polygon.size(); i++){
			Segment finalSegment = new Segment(polygon.get(0), polygon.get(i - 1));
			if (!alreadyUsedCopy.contains(finalSegment.vertices)){
				alreadyUsedCopy.add(finalSegment.vertices);
				List<Character> leftPolygon = new ArrayList<Character>(polygon.subList(0, i));
				List<Character> rightPolygon = new ArrayList<Character>(polygon.subList(i - 1, polygon.size()));
				rightPolygon.add(polygon.get(0));
				List<Set<Segment>> leftSolution = triangulate(leftPolygon, alreadyUsedCopy);
				List<Set<Segment>> rightSolution = triangulate(rightPolygon, alreadyUsedCopy);
				rightSolution.addAll(leftSolution);
				result.addAll(addFinalSegment(rightSolution, finalSegment));
			}
		}
		Segment finalSegment = new Segment(polygon.get(1), polygon.get(polygon.size() - 1));
		if (!alreadyUsedCopy.contains(finalSegment.vertices)){
			alreadyUsedCopy.add(finalSegment.vertices);
			List<Character> lastPolygon = new ArrayList<Character>(polygon.subList(1, polygon.size()));
			List<Set<Segment>> lastSolution = triangulate(lastPolygon, alreadyUsedCopy);
			result.addAll(addFinalSegment(lastSolution, finalSegment));
		}
		return result;
	}
	
	private List<Set<Segment>> addFinalSegment(List<Set<Segment>> solutionSet, Segment finalSegment){
		if (solutionSet.size() > 0)
			for (Set<Segment> solution : solutionSet){
				solution.add(finalSegment);
			}
		else
		{
			Set<Segment> newSolution = new HashSet<Segment>();
			newSolution.add(finalSegment);
			solutionSet.add(newSolution);
		}
		return solutionSet;
	}
	
	private class Segment{
		public Set<Character> vertices = new HashSet<Character>();
		public char firstVertex;
		public char secondVertex;
		public Segment(char firstVertex, char secondVertex){
			this.firstVertex = firstVertex;
			this.secondVertex = secondVertex;
			this.vertices.add(firstVertex);
			this.vertices.add(secondVertex);
		}
	}
}