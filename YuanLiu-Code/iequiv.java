import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class iequiv {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = args[0];
		readGraphFile(fileName);
		
		if(isSameSkeleton()){
			LinkedList<Immorality>  Imm1 = getImm(Edge1);
			LinkedList<Immorality>	Imm2 = getImm(Edge2);
			if(isSameImm(Imm1, Imm2)) System.out.println("True");
			else System.out.println("False");
		}else System.out.println("False");
		
	}
	
	static LinkedList<Edge> Edge1 = new LinkedList<Edge>();
	static LinkedList<Edge> Edge2 = new LinkedList<Edge>();
	static Set<Character> NodeSet = new HashSet<Character>();
	
	
	private static LinkedList<Immorality> getImm(LinkedList<Edge> EdgeSet){
		LinkedList<Immorality>  Imm = new LinkedList<Immorality>();
		for(char x: NodeSet){
			for(char y: NodeSet){
				for(char z: NodeSet){
					if(x!=y && x!=z && y!=z && (x<z)){
						if(EdgeSet.contains(new Edge(x,y)) && EdgeSet.contains(new Edge(z,y))){
							if((!EdgeSet.contains(new Edge(x,z))) && (!EdgeSet.contains(new Edge(z,x))))
								Imm.add(new Immorality(x,y,z));
						}
					}
				}
			}
		}
		return Imm;
	}
	
	
	private static boolean isSameImm(LinkedList<Immorality>  Imm1,LinkedList<Immorality>  Imm2){
		if(Imm1.size()!=Imm2.size()) {return false;}
		for(Immorality imm: Imm1){
			if(!Imm2.contains(imm))
				return false;
		}
		return true;
	}
	
	private static boolean isSameSkeleton(){
		if(Edge1.size()!=Edge2.size()) {return false;}
		for(Edge e: Edge1){
			if(!Edge2.contains(e) &&(!Edge2.contains(new Edge(e.Y, e.X))))
				return false;
			
		}
		return true;
	}
	
	private static void readGraphFile(String filePath){
		    File file = new File(filePath);
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            //ReadGraph one
	            tempString = reader.readLine();
	            String[] temp = tempString.split(" ");
	            int Edge_Number = Integer.valueOf(temp[1]);
	            for(int i=0; i< Edge_Number; i++){
	            	tempString = reader.readLine();
	            	char A = tempString.charAt(0);
	            	char B = tempString.charAt(2);
	            	if(!Edge1.contains(new Edge(A,B))) Edge1.add(new Edge(A,B));
	            	NodeSet.add(A);
	            	NodeSet.add(B);
	            }
	            //ReadGraph two
	            tempString = reader.readLine();
	            temp = tempString.split(" ");
	            Edge_Number = Integer.valueOf(temp[1]);
	            for(int i=0; i< Edge_Number; i++){
	            	tempString = reader.readLine();
	            	char A = tempString.charAt(0);
	            	char B = tempString.charAt(2);
	            	if(!Edge2.contains(new Edge(A,B))) Edge2.add(new Edge(A,B));
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
class Edge{
	char X;
	char Y;
	Edge(char a, char b){this.X=a; this.Y=b;}
	@Override
	public String toString(){return "["+Character.toString(X)+", "+Character.toString(Y)+"]";}
    @Override
    public boolean equals(Object object){
        boolean sameSame = false;
        if (object != null && object instanceof Edge){
            sameSame = (this.X == ((Edge) object).X) &&(this.Y == ((Edge) object).Y);
        }
        return sameSame;
    }
}

class Immorality{
	char X;
	char Y;
	char Z;
	Immorality(char a, char b, char c){this.X=a; this.Y=b; this.Z=c;}
	@Override
	public String toString(){return "["+Character.toString(X)+","+Character.toString(Y)+","+Character.toString(Z)+"]";}
    @Override
    public boolean equals(Object object){
        boolean sameSame = false;
        if (object != null && object instanceof Immorality){
            sameSame = (this.X == ((Immorality) object).X) && (this.Y == ((Immorality) object).Y) &&(this.Z == ((Immorality) object).Z);
        }
        return sameSame;
    }
}

