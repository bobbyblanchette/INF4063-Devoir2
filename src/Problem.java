import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Problem 
{
	public Problem(String polygon)
	{
		System.out.println("Polygone " + polygon);
		display(triangulate(process(polygon), new ArrayList<List<Character>>()));
	}
	
	private List<Character> process(String polygonStr){
		return polygonStr.chars().mapToObj(e->(char)e).collect(Collectors.toList());
	}
	
    private void display(List<List<Segment>> solutions){
    	for (int i = 0; i < solutions.size(); i++){
        	List<Segment> solution = solutions.get(i);
        	String line = (i+1) + ": ";
        	for (Segment segment : solution){
                line += "" + segment.firstVertex + segment.secondVertex + " ";
            }
        	System.out.println(line);
        }
    }

	private List<List<Segment>> triangulate(List<Character> polygon, List<List<Character>> alreadyUsed){
		List<List<Segment>> result = new ArrayList<List<Segment>>();
		List<List<Character>> alreadyUsedCopy = new ArrayList<List<Character>>(alreadyUsed); 
		if (polygon.size() <= 3)
			return new ArrayList<List<Segment>>();
		for (int i = 3; i < polygon.size(); i++){
			Segment finalSegment = new Segment(polygon.get(0), polygon.get(i - 1));
			if (!alreadyUsedCopy.contains(finalSegment.vertices)){
				alreadyUsedCopy.add(finalSegment.vertices);
				List<Character> leftPolygon = new ArrayList<Character>(polygon.subList(0, i));
				List<Character> rightPolygon = new ArrayList<Character>(polygon.subList(i - 1, polygon.size()));
				rightPolygon.add(polygon.get(0));
				List<List<Segment>> leftSolution = triangulate(leftPolygon, alreadyUsedCopy);
				List<List<Segment>> rightSolution = triangulate(rightPolygon, alreadyUsedCopy);
				List<List<Segment>> newSolution = combine(leftSolution, rightSolution);
				result.addAll(addFinalSegment(newSolution, finalSegment));
			}
		}
		Segment finalSegment = new Segment(polygon.get(1), polygon.get(polygon.size() - 1));
		if (!alreadyUsedCopy.contains(finalSegment.vertices)){
			alreadyUsedCopy.add(finalSegment.vertices);
			List<Character> lastPolygon = new ArrayList<Character>(polygon.subList(1, polygon.size()));
			List<List<Segment>> lastSolution = triangulate(lastPolygon, alreadyUsedCopy);
			result.addAll(addFinalSegment(lastSolution, finalSegment));
		}
		return result;
	}
	
	private List<List<Segment>> combine(List<List<Segment>> firstSolutionSet, List<List<Segment>> secondSolutionSet){
		if (firstSolutionSet.size() == 0){
			return secondSolutionSet;
		}
		if (secondSolutionSet.size() == 0){
			return firstSolutionSet;
		}
		List<List<Segment>> result = new ArrayList<List<Segment>>();
		for (List<Segment> firstSolution : firstSolutionSet){
			for (List<Segment> secondSolution : secondSolutionSet){
				List<Segment> newSolution = new ArrayList<Segment>(firstSolution);
				newSolution.addAll(secondSolution);
				result.add(newSolution);
			}
		}
		return result;
	}
	
	private List<List<Segment>> addFinalSegment(List<List<Segment>> solutionSet, Segment finalSegment){
		if (solutionSet.size() > 0)
			for (List<Segment> solution : solutionSet){
				solution.add(finalSegment);
			}
		else
		{
			List<Segment> newSolution = new ArrayList<Segment>();
			newSolution.add(finalSegment);
			solutionSet.add(newSolution);
		}
		return solutionSet;
	}
	
	private class Segment{
		public List<Character> vertices = new ArrayList<Character>();
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