import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SomeRaysTest extends PApplet {

public void settings()
{
  size(800, 800);
}
ArrayList<Wall> walls;
LightOrb orb;
float xStep = random(0, width);
float yStep = random(0, height);


public void setup()
{
  background(0);
  int howManyWalls = (int)random(2,6);
  walls = new ArrayList<Wall>();
  int wallColor = 0xffFFFFFF;
  for(int i = 0; i < howManyWalls; i++)
  {
    int randomColor = color(random(255), random(255), random(255));
    walls.add(new Wall(random(width), random(height), random(width), random(height), randomColor));
    print("done" + i);
  }
  walls.add(new Wall((float)0, (float)0, (float)width, (float)0, wallColor));
  walls.add(new Wall((float)width, (float)0, (float)width, (float)height, wallColor));
  walls.add(new Wall((float)0, (float)0, (float)0, (float)height, wallColor));
  walls.add(new Wall((float)0, (float)height, (float)width, (float)height, wallColor));

  float orbX = width * 0.5f;
  float orbY = height * 0.5f;
  orb = new LightOrb(1, orbX, orbY, color(255, 50));
  print(walls.size());
}

public void draw()
{
  clear();
  xStep += random(-0.005f, 0.01f);
  yStep += random(-0.005f, 0.01f);

  for(Wall wall : walls)
  {
    wall.ShowWall();
  }
  orb.MoveLightOrb(mouseX, mouseY);
  orb.ShowLightOrb();
  orb.ShootBeamsAtWalls(walls);
}
public class LightOrb
{
  float radius;
  float positionX;
  float positionY;
  int ballColor;
  ArrayList<Ray> rays = new ArrayList<Ray>();

  public LightOrb(float radius, float x, float y, int ballColor)
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
  
  public void ShowLightOrb()
  {
    noStroke();
    circle(positionX, positionY, radius);
    for(Ray ray : rays)
    {
      ray.UpdateCenter(mouseX, mouseY);
      ray.ShowRay();
    }
  }

  public void ShootBeamsAtWalls(ArrayList<Wall> walls)
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

  public void MoveLightOrb(float valueX, float valueY)
  {
    positionX = valueX;
    positionY = valueY;
  }
}
public class Ray
{
  PVector position;
  PVector direction;
  int rayColor;
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
public class Wall
{
  PVector beginPos;
  PVector endPos;
  int wallColor;

  public Wall(float x1, float y1, float x2, float y2, int wallColor)
  {
    this.beginPos = new PVector(x1, y1);
    this.endPos = new PVector(x2, y2);
    this.wallColor = wallColor;
  }
  
  public void ShowWall()
  {
    stroke(wallColor);
    line(beginPos.x, beginPos.y, endPos.x, endPos.y);
  }
}
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SomeRaysTest" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
