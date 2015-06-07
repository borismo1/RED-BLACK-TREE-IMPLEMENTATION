// Name: Boris Morozov 
// I.D.: 314396177
//
// i'm sorry i wasn't able to find a partner because i started doing it to late everyone already had partners .


/**
 *
 * RBTree
 *
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer keys and values
 *
 */

public class RBTree {


	
	//fields:min pointer to current node with minimal key, max same as min , sentinel-root and sentinel-leaf are self explanatory, 
	//since sentinel-leaf can be extra black and we have 1 extra black node at all times i made a separete sentinel for this case
	//color_switches and size is counters updated at insertion and deletion 
	
	RBNode min;
	RBNode max;
	RBNode sentinelRoot;
	RBNode sentinelLeaf;
	RBNode sentinelLeafExtraBlack;
	RBNode extraBlack;
	int size;
	int color_switches;
	
	
	public RBTree(){
		this.sentinelLeaf = new RBNode(null, null, null, "sentinelLeaf", -1, "BLACK");
		this.sentinelRoot = new RBNode(this.sentinelLeaf, this.sentinelLeaf, null, "sentinelRoot", -1, "BLACK");
		this.sentinelLeafExtraBlack = new RBNode(null, null, null, "sentinelLeadExtraBlack", -1, "BLACK") ;
		this.min = this.sentinelRoot;
		this.max = this.sentinelLeaf;
		this.size = 0;
		this.color_switches = 0;
		this.extraBlack = null;
	}
	
	
	//updatine min,max,size when insering and deleting
	
	public void updateMinMaxSizeWhenInsert(RBNode node){
		if(this.size == 0){
			this.min = node;
			this.max = node;
		}
		else{
			if(this.min.key > node.key && node.key != -1){
				this.min = node;
			}
			if(this.max.key < node.key){
				this.max = node;
			}
		}
		this.size++;
	}
	
	public void updateMinMaxSizeWhenDelete(RBNode node){
		if(this.max.key == node.key){
			this.max = node.parent;
		}
		if(this.min.key == node.key){
			this.min = node.parent;
		}
		this.size--;
	}
	
	//get succsessor of the given node in this tree context
	
	public RBNode getSuccessor(RBNode node){
		   if(node.right.key == -1){
			   return this.sentinelLeaf;
		   }
		   node = node.right;
		   while(node.left.key != -1){
			   node = node.left;
		   }
		   return node;
	   }
	
	
	//switches colors and update the counter (color_switches)
	
	public void switchColor(RBNode node){
		  if(node.color == "RED"){
			  node.color = "BLACK";
		  }
		  else{
			  node.color = "RED";
		  }
		  this.color_switches++;
	  }
	
	//if a given node is a left child return ture else return false
	
	public boolean isLeft(RBNode node){
		if(node.key != -1){
			if(node.parent.left.key == node.key){
				return true;
			}
			return false;
		}
		else{
			if(node.parent.left.value == node.value){
				return true;
			}
			return false;
		}
	}
	
	//same but with a key instead of a node
	public boolean isLeft(int k){
		RBNode node = this.sentinelRoot.left.binarySearch(k);
		return this.isLeft(node);
	}
	
	
	//returns uncle of a give node in this tree context
	public RBNode getUncle(RBNode node){
		if(node.parent.key == -1 || node.parent.parent.key == -1){
			return this.sentinelLeaf;
		}
		else{
			if(node.parent.key < node.parent.parent.key){
				return node.parent.parent.right;
			}
			else{
				return node.parent.parent.left;
			}
		}
	}
	
	//returns sibling of a given node in this tree context
	
	  public RBNode getSibling(RBNode node){
		  if(this.isLeft(node) && node.parent.right.key != -1){
			  return node.parent.right;
		  }
		  else if( !this.isLeft(node) && node.parent.left.key != -1){
			  return node.parent.left;
		  }
		  else{
			  return this.sentinelLeaf;
		  }
	  }
	
  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   * Complexity = O(1) 
   */
	public boolean empty() {
    return this.size == 0? true:false;
  }

 /**
   * public String search(int k)
   *
   * returns the value of an item with key k if it exists in the tree
   * otherwise, returns null
   * 
   * Complexity = O(log(n)) (binary search)
   * 
   */
	public String search(int k){
	return this.sentinelRoot.left.binarySearch(k).key == k? this.sentinelRoot.left.binarySearch(k).value : null;
  }

  /**
   * public int insert(int k, String v)
   *
   * inserts an item with key k and value v to the red black tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of color switches, or 0 if no color switches were necessary.
   * returns -1 if an item with key k already exists in the tree.
   * 
   * Complexity = O(Log(n)) (every time we push out problem 1 level up)
   */
	public int insert(int k, String v) {
	 if(this.size == 0){
		 this.sentinelRoot.left = new RBNode(this.sentinelLeaf, this.sentinelLeaf, this.sentinelRoot, v, k, "BLACK");
		 this.updateMinMaxSizeWhenInsert(this.sentinelRoot.left);
		 return 0;
	 }
	 else{
		 RBNode whereToInsert = this.sentinelRoot.left.binarySearch(k);
		 if(whereToInsert.key == k){
			 return -1;
		 }
		 else if(whereToInsert.key > k){
			 whereToInsert.left = new RBNode(sentinelLeaf, sentinelLeaf, whereToInsert, v, k, "RED");
			 this.updateMinMaxSizeWhenInsert(whereToInsert.left);
			 this.color_switches = 0;
			 this.insertFix(whereToInsert.left);
			 return color_switches;
		 }
		 else if(whereToInsert.key < k){
			 whereToInsert.right = new RBNode(sentinelLeaf, sentinelLeaf, whereToInsert, v, k, "RED");
			 this.updateMinMaxSizeWhenInsert(whereToInsert.right);
			 this.color_switches = 0;
			 this.insertFix(whereToInsert.right);
			 return color_switches;
		 }
	 }
	return color_switches;
   }
	
	
	// restoring the tree invariants after inserion (loop 1 - case 1 , loop 2 - case 2 and so on ) 
	
	 private void insertFix(RBNode insertedNode){
		   this.sentinelRoot.color = "BLACK";
		   this.sentinelRoot.left.color ="BLACK";
		   RBNode uncle = this.getUncle(insertedNode);
		   if(insertedNode.parent.color == "RED" && uncle.color == "RED" ){
			   this.switchColor(insertedNode.parent);
			   this.switchColor(uncle);
			   this.switchColor(insertedNode.parent.parent);
			   insertFix(insertedNode.parent.parent);
		   }
		   else if(insertedNode.parent.color == "RED" && uncle.color == "BLACK"  && insertedNode.parent.key < insertedNode.parent.parent.key){
			   if(insertedNode.key > insertedNode.parent.key){
				   rotateLeft(insertedNode.parent, insertedNode);
				   insertFix(insertedNode.left);
			   }
			   else{
				   this.switchColor(insertedNode.parent.parent);
				   this.switchColor(insertedNode.parent);
				   rotateRight(insertedNode.parent.parent, insertedNode.parent);
			   }
		   }
		   else if(insertedNode.parent.color == "RED" && uncle.color == "BLACK"  && insertedNode.parent.key > insertedNode.parent.parent.key){
			   if(insertedNode.key < insertedNode.parent.key){
				   rotateRight(insertedNode.parent, insertedNode);
				   insertFix(insertedNode.right);
			   }
			   else{
				   this.switchColor(insertedNode.parent.parent);
				   this.switchColor(insertedNode.parent);
				   rotateLeft(insertedNode.parent.parent, insertedNode.parent);
			   }
		   }
	   }

  /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of color switches, or 0 if no color switches were needed.
   * returns -1 if an item with key k was not found in the tree.
   * 
   * Complexity = O(log(n)) (same here, every time we solve 1 level)
   */
	public int delete(int k){
		this.color_switches = 0;
		if(k == -1){
			return 0;
		}
		RBNode node  = this.sentinelRoot.left.binarySearch(k);
		if(node.key != k){
			return -1;
		}
		if(node.left.key == -1 && node.right.key == -1){
			this.deleteLeaf(node);
		}
		else if(node.left.key != -1 && node.right.key != -1){
			this.deleteNodeWithBothChildern(node);
		}
		else{
			this.deleteNodeWithOneChild(node);
		}
		while(this.extraBlack != null){
			this.solveExtraBlackness(this.extraBlack);
		}
		return this.color_switches;
   }
	
	
	//delete a node if its an internal leaf
	
	public void deleteLeaf(RBNode node){
		if(this.isLeft(node)){
			if(node.color == "BLACK"){
				node.parent.left = this.sentinelLeafExtraBlack;
				this.sentinelLeafExtraBlack.parent = node.parent;
				this.extraBlack = this.sentinelLeafExtraBlack;
			}
			else{
				node.parent.left = this.sentinelLeaf;
			}
		}
		else{
			if(node.color == "BLACK"){
				node.parent.right = this.sentinelLeafExtraBlack;
				this.sentinelLeafExtraBlack.parent = node.parent;
				this.extraBlack = this.sentinelLeafExtraBlack;
			}
			else{
				node.parent.right = this.sentinelLeaf;
			}
		}
	}
	
	//delete a node if it have one child in BST context
	
	public void deleteNodeWithOneChild(RBNode node){
		if(this.isLeft(node)){
			if(node.left.key != -1 && node.right.key == -1){
				if(node.left.color == "BLACK"){
					node.parent.left = node.left;
					node.left.parent = node.parent;
					this.extraBlack = node.left;
				}
				else{
					node.parent.left = node.left;
					node.left.parent = node.parent;
					this.switchColor(node.left);
				}
			}
			else if(node.left.key == -1 && node.right.key != -1){
				if(node.right.color == "BLACK"){
					node.parent.left = node.right;
					node.right.parent = node.parent;
					this.extraBlack = node.right;
				}
				else{
					node.parent.left = node.right;
					node.right.parent = node.parent;
					this.switchColor(node.right);
				}
			}
		}
		else{
			if(node.left.key != -1 && node.right.key == -1){
				if(node.left.color == "BLACK"){
					node.parent.right = node.left;
					node.left.parent = node.parent;
					this.extraBlack = node.left;
				}
				else{
					node.parent.right = node.left;
					node.left.parent = node.parent;
					this.switchColor(node.left);
				}
			}
			else if(node.left.key == -1 && node.right.key != -1){
				if(node.right.color == "BLACK"){
					node.parent.right = node.right;
					node.right.parent = node.parent;
					this.extraBlack = node.right;
				}
				else{
					node.parent.right = node.right;
					node.right.parent = node.parent;
					this.switchColor(node.right);
				}
			}
		}
	}

	//deletes a node with two childern in BST context

	public void deleteNodeWithBothChildern(RBNode node){
		RBNode successor = this.getSuccessor(node);
		if(successor.left.key == -1 && successor.right.key == -1){
			this.deleteLeaf(successor);
		}
		else if(successor.left.key == -1 && successor.right.key != -1){
			this.deleteNodeWithOneChild(successor);
		}
		successor.color = node.color;
		successor.left = node.left;
		successor.right = node.right;
		successor.parent = node.parent;
		if(successor.left.key != -1 || successor.left.value == "sentinelLeadExtraBlack"){
			successor.left.parent = successor;
		}
		if(successor.right.key != -1 || successor.right.value == "sentinelLeadExtraBlack"){
			successor.right.parent = successor;
		}
		if(this.isLeft(node)){
			node.parent.left = successor;
		}
		else{
			node.parent.right =successor;
		}
	}
	
	//solves extra black problems by cases as learned in class 
	
	public void solveExtraBlackness(RBNode node){
		this.sentinelLeaf.color = "BLACK";
		this.sentinelRoot.color = "BLACK";
		if(node.parent.value == "sentinelRoot"){
			this.extraBlack = null ;
			return;
		}
		if(this.getSibling(node).color == "RED"){
			solveExtraBlacknessCase1(node);
		}
		else if(this.getSibling(node).color == "BLACK" && this.getSibling(node).key != -1 && this.getSibling(node).left.color == "BLACK" &&this.getSibling(node).right.color == "BLACK"){
			solveExtraBlacknessCase2(node);
		}

			
		else if((this.isLeft(node) && node.parent.right.left.color == "RED" && node.parent.right.right.color == "BLACK") 
				|| (!this.isLeft(node) && node.parent.left.left.color == "BLACK" && node.parent.left.right.color == "RED" ) ){
			solveExtraBlacknessCase3(node);
		}
		else{
			solveExtraBlacknessCase4(node);
		}
	}
	
	
	
	
	
	public void solveExtraBlacknessCase1(RBNode node){
		this.switchColor(node.parent);
		if(this.isLeft(node)){
			this.switchColor(node.parent.right);
			rotateLeft(node.parent, node.parent.right);
		}
		else{
			this.switchColor(node.parent.left);
			rotateRight(node.parent, node.parent.left);
		}
	}
	
	public void solveExtraBlacknessCase2(RBNode node){
		if(node.parent.color == "BLACK"){
			this.extraBlack = node.parent;
		}
		else{
			this.extraBlack = null;
			this.switchColor(node.parent);
			
		}
		if(this.isLeft(node)){
			this.switchColor(node.parent.right);
		}
		else{
			this.switchColor(node.parent.left);
		}
		if(node.key == -1){
			if(this.isLeft(node)){
				node.parent.left = this.sentinelLeaf;
			}
			else{
				node.parent.right = this.sentinelLeaf;
			}
			this.sentinelLeafExtraBlack.parent = null;
		}
	}
	
	public void solveExtraBlacknessCase3(RBNode node){
		this.switchColor(this.getSibling(node));
		if(this.isLeft(node)){
			this.switchColor(node.parent.right.left);
			rotateRight(node.parent.right, node.parent.right.left);
		}
		else{
			this.switchColor(node.parent.left.right);
			rotateLeft(node.parent.left, node.parent.left.right);
		}
	}
	
	public void solveExtraBlacknessCase4(RBNode node){
		if(node.parent.color == "RED"){
			this.switchColor(this.getSibling(node));
			this.switchColor(node.parent);
		}
		if(this.isLeft(node)){
			this.switchColor(this.getSibling(node).right);
			rotateLeft(node.parent, this.getSibling(node));
		}
		else{
			this.switchColor(this.getSibling(node).left);
			rotateRight(node.parent, this.getSibling(node));
		}
		this.extraBlack = null;
		if(node.key == -1){
			if(this.isLeft(node)){
				node.parent.left = this.sentinelLeaf;
			}
			else{
				node.parent.right = this.sentinelLeaf;
			}
			this.sentinelLeafExtraBlack.parent = null;
		}
	}
	
	
   /**
    * public String min()
    *
    * Returns the value of the item with the smallest key in the tree,
    * or null if the tree is empty
    * 
    * Complexity = O(1)
    */
	public String min()
   {
	   return this.min.value;
   }

   /**
    * public String max()
    *
    * Returns the value of the item with the largest key in the tree,
    * or null if the tree is empty
    * Complexity = O(1)
    */
	public String max()
   {
	   return this.max.value;
   }

  /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   * 
   * Complexity = O(n) (every step we printout 1 node)
   */
	
	int index ;
	public int[] keysToArray(){
		index = 0;
		int[] array = new int[this.size]; 
		inOrder(this.sentinelRoot.left, array, index); 
        return array;
  }

	public void inOrder(RBNode node,int[] array, int pointer){
	    if(node.key == -1){ 
	       return;
	    }
	    inOrder(node.left, array, index);  
	    array[index]= node.key;     
	    index++;
	    inOrder(node.right, array, index);  
	}
  /**
   * public String[] valuesToArray()
   *
   * Returns an array which contains all values in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
	public String[] valuesToArray(){
		index = 0;
		String[] array = new String[this.size]; 
		inOrderString(this.sentinelRoot.left, array, index); 
        return array;            
  }
	
	public void inOrderString(RBNode node,String[] array, int pointer){
	    if(node.key == -1){ 
	       return;
	    }
	    inOrderString(node.left, array, index);  
	    array[index]= node.value;     
	    index++;
	    inOrderString(node.right, array, index);  
	}

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    * precondition: none
    * postcondition: none
    * 
    * Complexity = O(1)
    */
	public int size()
   {
	   return this.size;
   }
   
   
   //rotateRight and rotateLeft where the relative-root before the Rotation is "root" and after rotation is "pivot"
   // Complexity = O(1)
	
  	public void rotateRight(RBNode root,RBNode pivot){
  		root.left = pivot.right;
  		if(root.left.key != -1){
  			root.left.parent = root;
  		}
  		pivot.right = root;
  		pivot.parent = root.parent;
  		root.parent = pivot;
  		if(pivot.key > pivot.parent.key && pivot.parent.key != -1){
  			pivot.parent.right = pivot;
  		}
  		else{
  			pivot.parent.left = pivot;
  		}
  	}

  	public void rotateLeft(RBNode root,RBNode pivot){
  		root.right = pivot.left;
  		if(root.right.key != -1){
  			root.right.parent = root;
  		}
  		pivot.left = root;
  		pivot.parent = root.parent;
  		root.parent = pivot;
  		if(pivot.key > pivot.parent.key && pivot.parent.key != -1){
  			pivot.parent.right = pivot;
  		}
  		else{
  			pivot.parent.left = pivot;
  		}
  	}
  	
  	

  /**
   * public class RBNode
   *
   * If you wish to implement classes other than RBTree
   * (for example RBNode), do it in this file, not in 
   * another file.
   * This is an example which can be deleted if no such classes are necessary.
   */
  public class RBNode{
	  RBNode left;
	  RBNode right;
	  RBNode parent;
	  String value;
	  int key;
	  String color;
	  
	  public RBNode(RBNode l,RBNode r,RBNode p,String v,int k,String c){
		  this.left = l;
		  this.right = r;
		  this.parent = p;
		  this.value = v;
		  this.key = k;
		  this.color =c;
	  }
	  
	  
	  public RBNode binarySearch(int k){
			if(this.key == k){
				return this;
			}
			else if(this.key < k){
				if(this.right.key == -1){
					return this;
				}
				else{
					return this.right.binarySearch(k);
				}
			}
			else{
				if(this.left.key == -1){
					return this;
				}
				else{
					return this.left.binarySearch(k);
				}
			}
		}
	  
  }
  

  
  
  
}