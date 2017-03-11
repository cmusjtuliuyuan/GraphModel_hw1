import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class dsep {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filePath =args[0];
		readGraphFile(filePath);
		//System.out.println(Parent_Set.toString());
		//System.out.println(Child_Set.toString());
		for(String query : QueryList){
			LinkedList<Character> evidence = new LinkedList<Character>();
			String path = Parser(query, evidence);
			char A = path.charAt(0);
			char B = path.charAt(2);
		
			Set<Character> ancestor = findAncestor(evidence);
			Set<Character>  Reach = Reachable(evidence, ancestor, A);
			if(Reach.contains(B)) System.out.println("False");
				else  System.out.println("True");
		}
	}
	
    static int Node_Number=0;
    static int Edge_Number=0;
    static int Query_Number=0;
    static ArrayList<String> QueryList = new ArrayList<String>();
    static HashMap<Character, Set<Character>> Parent_Set = new HashMap<Character, Set<Character>>();
    static HashMap<Character, Set<Character>> Child_Set = new HashMap<Character, Set<Character>>();
	
	private static String Parser(String query, LinkedList<Character> evidence){
		char A = query.charAt(0);
		char B = query.charAt(2);
		String evidence_string = query.substring(5, query.length());
		String[] evidence_array = evidence_string.split(" ");
		int array_size = evidence_array.length;
		for(int i=1; i< array_size; i++) evidence.add(evidence_array[i].charAt(0));
		return A+" "+B;
	}
	
	
	
	private static Set<Character> Reachable(LinkedList<Character> Z, Set<Character> A ,char X){
		LinkedList<Pair> L = new LinkedList<Pair>();
		L.add(new Pair(X,true));
		LinkedList<Pair> V = new LinkedList<Pair>();
		Set<Character> R = new HashSet<Character>();
		
		while(!L.isEmpty()){
			Pair Yd = L.getFirst();
			L.removeFirst();
			if(!V.contains(Yd)){
				if(!Z.contains(Yd.Y))
					R.add(Yd.Y);  //Y is reachable
				V.addLast(Yd);   // Mark (Y,d) as visited
				if(Yd.d && (!Z.contains(Yd.Y))){
					Set<Character> PaY = Parent_Set.get(Yd.Y);
					if(PaY!=null)
						for(char z : PaY) if(!L.contains(new Pair(z,true))) L.addLast(new Pair(z,true));
					Set<Character> ChY = Child_Set.get(Yd.Y);
					if(ChY!=null)
						for(char z : ChY) if(!L.contains(new Pair(z,false))) L.addLast(new Pair(z,false));	
				}else if(!Yd.d){
					if(!Z.contains(Yd.Y)){
						Set<Character> ChY = Child_Set.get(Yd.Y);
						if(ChY!=null)
							for(char z : ChY) if(!L.contains(new Pair(z,false))) L.addLast(new Pair(z,false));
					}
					if(A.contains(Yd.Y)){
						Set<Character> PaY = Parent_Set.get(Yd.Y);
						if(PaY!=null)
							for(char z : PaY) if(!L.contains(new Pair(z,true))) L.addLast(new Pair(z,true));
					}
				}
			}
		}
		return R;
	}
	
	private static Set<Character> findAncestor(LinkedList<Character> evidence){
		@SuppressWarnings("unchecked")
		LinkedList<Character> L = (LinkedList<Character>) evidence.clone();
		HashSet<Character> A = new HashSet<Character>();
		while(!L.isEmpty()){
			char Y = L.getFirst();
			L.removeFirst();
			if(!A.contains(Y)){
				if(Parent_Set.containsKey(Y)){
					Set<Character> PaY= Parent_Set.get(Y);
					for(char father : PaY){
						if(!L.contains(father)) {
							L.addLast(father);
						}
					}
				}
			}	
			A.add(Y);
		}
		
		return A;
	} 
	private static void readGraphFile(String filePath){
		 File file = new File(filePath);
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            tempString = reader.readLine();
	            String[] temp = tempString.split(" ");
	            Node_Number = Integer.valueOf(temp[0]);
	            Edge_Number = Integer.valueOf(temp[1]);
	            Query_Number = Integer.valueOf(temp[2]);
	            for(int i=0; i<Edge_Number; i++){
	            	tempString = reader.readLine();
	            	char father_node = Character.valueOf(tempString.charAt(0));
	            	char child_node = Character.valueOf(tempString.charAt(2));
	            	if(!Parent_Set.containsKey(child_node)){
		            	HashSet<Character> temp_parent_set = new HashSet<Character>();
		            	temp_parent_set.add(father_node);
	            		Parent_Set.put(child_node, temp_parent_set);	            		
	            	}else{
	            		Parent_Set.get(child_node).add(father_node);
	            	}
	            	
	            	if(!Child_Set.containsKey(father_node)){
	            		Set<Character> temp_child_set = new HashSet<Character>();
	            		temp_child_set.add(child_node);
	            		Child_Set.put(father_node, temp_child_set);
	            	}else{
	            		Set<Character> temp_child_set = Child_Set.get(father_node);
	            		temp_child_set.add(child_node);
	            	}
	            }
	            for(int i=0; i < Query_Number; i++){
	            	tempString = reader.readLine();
	            	QueryList.add(tempString);
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
	}
}

class Pair{
	char Y;
	boolean d;
	Pair(char a, boolean b){this.Y=a; this.d=b;}
	@Override
	public String toString(){return "Y="+Character.toString(Y)+", Boolean="+Boolean.toString(d)+";";}
    @Override
    public boolean equals(Object object){
        boolean sameSame = false;
        if (object != null && object instanceof Pair){
            sameSame = (this.Y == ((Pair) object).Y) &&(this.d == ((Pair) object).d);
        }
        return sameSame;
    }
}
