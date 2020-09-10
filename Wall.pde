public class Wall
{
  PVector beginPos;
  PVector endPos;
  color wallColor;

  public Wall(float x1, float y1, float x2, float y2, color wallColor)
  {
    this.beginPos = new PVector(x1, y1);
    this.endPos = new PVector(x2, y2);
    this.wallColor = wallColor;
  }
  
  void ShowWall()
  {
    stroke(wallColor);
    line(beginPos.x, beginPos.y, endPos.x, endPos.y);
  }
}
