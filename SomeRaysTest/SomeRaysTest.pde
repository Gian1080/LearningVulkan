void settings()
{
  size(800, 800);
}
ArrayList<Wall> walls;
LightOrb orb;
float xStep = random(0, width);
float yStep = random(0, height);


void setup()
{
  background(0);
  int howManyWalls = (int)random(2,6);
  walls = new ArrayList<Wall>();
  color wallColor = #FFFFFF;
  for(int i = 0; i < howManyWalls; i++)
  {
    color randomColor = color(random(255), random(255), random(255));
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

void draw()
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
