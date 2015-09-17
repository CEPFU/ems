package de.fu_berlin.agdb.crepe.core;

public class Triple<S1, S2, S3> {

	private S1 left;
	private S2 middle;
	private S3 right;
	
	public Triple(S1 left, S2 middle, S3 right) {
		this.left = left;
		this.middle = middle;
		this.right = right;
	}

	public S1 getLeft() {
		return left;
	}
	
	public S2 getMiddle() {
		return middle;
	}

	public S3 getRight() {
		return right;
	}
}
