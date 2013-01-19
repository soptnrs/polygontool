

public class BoundingBox2D {
	private float minX,minY,maxX,maxY;
	
	public BoundingBox2D(){
		this.set(0,0,0,0);
	}
	public BoundingBox2D(float min_X, float min_Y, float max_X, float max_Y){
		this.set(min_X,min_Y,max_X,max_Y);
	}
	
	public void set(float min_X, float min_Y, float max_X, float max_Y){
		this.minX = min_X;
		this.minY = min_Y;
		this.maxX = max_X;
		this.maxY = max_Y;
	}
	
	public boolean overlap(BoundingBox2D b){
		
		if(this.maxX < b.minX)
			return false;
		else if(this.minX > b.maxX)
			return false;
		if(this.maxY < b.minY)
			return false;
		else if(this.minY > b.maxY)
			return false;
		return true;
	}
	public float getMinX(){
		return this.minX;
	}
	public float getMinY(){
		return this.minY;
	}
	public float getMaxX(){
		return this.maxX;
	}
	public float getMaxY(){
		return this.maxY;
	}
	
}
