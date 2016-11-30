import java.util.ArrayList;
import java.util.List;

public class Problem 
{
	List<String> solutions = new ArrayList<String>();;
	int n;
	public Problem(String polygon)
	{
		n = polygon.length();
		System.out.println("Polygone " + polygon);
		triangulate(process(polygon));
		for(String solution : solutions)
			System.out.println(solution);
	}
	
	private Polygon process(String polygon){
		List<Vertex> vertexArray = new ArrayList<Vertex>();
		for (char vertexName : polygon.toCharArray()){
			Vertex temp = new Vertex(vertexName);
			if(vertexArray.size() > 0)
				temp.addAdjacent(vertexArray.get(vertexArray.size() - 1));
			vertexArray.add(temp);
		}
		vertexArray.get(0).addAdjacent(vertexArray.get(vertexArray.size() - 1));
		
		return new Polygon(vertexArray);
	}
	
	private String solution = "";
	private void triangulate(Polygon polygon){
		for (int i = 3; i < polygon.polygon.size(); i++){
			boolean second = false;
			for (Polygon subPolygon : polygon.splitPolygon(i)){
				if (second && subPolygon.polygon.size() <= 3){
					solutions.add(solution);
				}
				else{
					second = true;
					triangulate(subPolygon);
				}
			}
			
		}
		if (polygon.polygon.size() <= 3){
			solution += polygon.getEdge();
		}
	}
	
	private void display(List<String> solution){
		for (String triangulation : solution){
			System.out.println(triangulation);
		}
	}
	
	private class Polygon{
		public List<Vertex> polygon;
		public Polygon(List<Vertex> polygon){
			this.polygon = polygon;
		}
		public Vertex get(int i){
			return polygon.get(i % polygon.size());
		}
		public String getEdge(){
			return "" + polygon.get(0).name + polygon.get(polygon.size() - 1).name + " ";
		}
		public List<Polygon> splitPolygon(int index){
			List<Polygon> result = new ArrayList<Polygon>();
			result.add(new Polygon(new ArrayList<Vertex>(this.polygon.subList(0, index))));
			List<Vertex> temp = new ArrayList<Vertex>(this.polygon.subList(index - 1, this.polygon.size()));
			temp.add(get(polygon.size()));
			result.add(new Polygon(temp));
			return result;
		}
	}
	
	private class Vertex{
		public char name;
		public List<Vertex> adjacents = new ArrayList<Vertex>();
		public Vertex(char name){
			this.name = name;
		}
		public Vertex addAdjacent(Vertex adjacent){
			this.adjacents.add(adjacent);
			adjacent.adjacents.add(this);
			return this;
		}
	}
}