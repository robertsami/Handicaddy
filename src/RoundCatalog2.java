import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import com.summer.handicaddy.Course;
import com.summer.handicaddy.Date;
import com.summer.handicaddy.Round;
import com.summer.handicaddy.TeeBox;

/**
 * 
 */

/**
 * @author rsami
 *
 */
public class RoundCatalog2 implements Serializable {

	private static final long serialVersionUID = 2L;

	private class Node implements Serializable {
		private static final long serialVersionUID = 7275432282107618077L;
		private Node nextNode; //points to node with next chronological round
		private Node lastNode; //points to node with last chronological round
		private Round round;
		private Node lastPlayCourse; //points to the last round at given course
		private Node lastPlayTeeBox; //points to the last round at given tee box
		public Date day; // denotes the Date of this node
		
		/**
		 * Array contains all references for the node
		 * index 0: lastNode
		 * index 1: nextNode
		 * index 2: lastPlayCourse
		 * index 3: lastPlayTeeBox
		 */
		private Node[] references = new Node[4]; 
		
		private Node getLastNode() { return references[0]; }
		private Node getNextNode() { return references[1]; }
		private Node getLastPlayCourse() { return references[2]; }
		private Node getLastPlayTeeBox() { return references[3]; }
		
		private void setLastNode(Node node) { references[0] = node; }
		private void setNextNode(Node node) { references[1] = node; }
		private void setLastPlayCourse(Node node) { references[2] = node; }
		private void setLastPlayTeeBox(Node node) { references[3] = node; }
		
		/**
		 * Connect this to node, ie this.next = node and node.last = this
		 * @param node
		 */
		private void connect(Node node)
		{
			setNextNode(node);
			node.setLastNode(this);
		}
		
	}
	
	ArrayList<Node> days; //provides map from day to node
	HashMap<Course, Node> courses; //provides map from course to network of nodes at given course
	HashMap<TeeBox, Node> teeBoxes; //provides map from teeBox to network of nodes at given teeBox

	private Date day1;
	private Node end;
	
	
	public RoundCatalog2(Date startDate)
	{
		day1 = startDate;
		
		end = new Node();
		end.connect(end);
		
		
		
	}
	
	
	
	
	
}
