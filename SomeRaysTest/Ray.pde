public class Ray
{
  PVector position;
  PVector direction;
  color rayColor;
  public Ray(PVector pos, float angle)
  {
    this.position = pos;
    this.direction = PVector.fromAngle(angle);
  }

  public void ShowRay()
  {
    stroke(255,4);
    push();
    translate(this.position.x, this.position.y);
    line(0, 0, this.direction.x * 10, this.direction.y * 10);
    pop();
  }

  public void LookDirection(float x, float y)
  {
    direction.x = x - position.x;
    direction.y = y - position.y;
    direction.normalize();
  }

  public void UpdateCenter(float x, float y)
  {
    position.x = x;
    position.y = y;
  }


  public PVector IntersectPoint(Wall wall)
  {
    float x1 = wall.beginPos.x;
    float y1 = wall.beginPos.y;
    float x2 = wall.endPos.x;
    float y2 = wall.endPos.y;

    float x3 = position.x;
    float y3 = position.y;
    float x4 = position.x + direction.x;
    float y4 = position.y + direction.y;

    float denominator = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
    if(denominator == 0)
    {
      return null;
    }
    float t = ((x1 - x3) * (y3 - y4) - (y1 - y3) * (x3 - x4)) / denominator;
    float u = -((x1 - x2) * (y1 - y3) - (y1 - y2) * (x1 - x3)) / denominator;

    if(t > 0 && t < 1 && u > 0)
    {
      PVector point = new PVector();
      point.x = x1 + t * (x2 - x1);
      point.y = y1 + t * (y2 - y1);
      return point;
    }
    else
    {
      return null;
    }
  }
  
}
