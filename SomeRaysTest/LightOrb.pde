public class LightOrb
{
  float radius;
  float positionX;
  float positionY;
  color ballColor;
  ArrayList<Ray> rays = new ArrayList<Ray>();

  public LightOrb(float radius, float x, float y, color ballColor)
  {
    this.radius = radius;
    this.positionX = x;
    this.positionY = y;
    this.ballColor = ballColor;
    for(int i = 0, r = 0; i < 360; i+= 1, r++)
    {
      PVector pos = new PVector(positionX, positionY);
      this.rays.add(new Ray(pos, radians(i)));
    }
  }
  
  void ShowLightOrb()
  {
    noStroke();
    circle(positionX, positionY, radius);
    for(Ray ray : rays)
    {
      ray.UpdateCenter(mouseX, mouseY);
      ray.ShowRay();
    }
  }

  void ShootBeamsAtWalls(ArrayList<Wall> walls)
  {
    for(Ray ray : rays)
    {
      PVector closest = null;
      PVector record = new PVector(width * width, height * height);
      float recordMagnitude = record.mag();
      for(Wall wall : walls)
      {
        PVector pos = new PVector(positionX, positionY);
        PVector point = ray.IntersectPoint(wall);
        
        if(point != null)
        {
          float distance = PVector.dist(pos, point);
          if(distance < recordMagnitude)
          {
            recordMagnitude = distance;
            closest = point;
            ray.rayColor = wall.wallColor;
          }
        }
      }
      if(closest != null)
      {
        stroke(ray.rayColor, 100);
        line(positionX, positionY, closest.x, closest.y);
      }
    }
  }

  void MoveLightOrb(float valueX, float valueY)
  {
    positionX = valueX;
    positionY = valueY;
  }
}
