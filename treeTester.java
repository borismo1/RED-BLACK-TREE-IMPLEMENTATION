import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.JEditorPane;






public class treeTester {

	
	
	
	public static void main(String[] args) {
		RBTree tree1 = new RBTree();
		ArrayList<Integer> keys = new ArrayList<>();
		Random rnd = new Random();
		int suminsert =0;
		int sumdelete =0;
		for(int j =1; j < 10;j++){
			for(int i =0 ; i<10000*j;i++){
				keys.add(rnd.nextInt(100000000));
		}
		}
		Collections.shuffle(keys);
		for(int j =1;j<11;j++){
			for(int i=0; i<10000*j;i++){
				int toto = tree1.insert(keys.get(i), Integer.toString(keys.get(i)));
				if(toto > 0){
					suminsert = suminsert + toto;
				}
			}
//			System.out.println(suminsert);
		}
		System.out.println(tree1.search(keys.get(11)));
		System.out.println(keys.get(11));
		int[] temp = tree1.keysToArray();	
		System.out.println("\n");
		for(int j =1;j<11;j++){
			for(int i=0; i<9000*j;i++){
				int toto = tree1.delete(temp[i]);
				if(toto > 0){
					sumdelete = sumdelete + toto;
				}
			}
//		System.out.println(sumdelete);
	}
	
	
	

	}
	}

