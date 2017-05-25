package com.company;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.IntStream;
import static java.lang.Math.*;


public class Vector {

    private int X;
    private int Y;
    private int Z;

    public Vector(int x, int y, int z)
    {
        this.X = x;
        this.Y = y;
        this.Z = z;
    }

    public int getX()
    {
        return this.X;
    }

    public int getY()
    {
        return this.Y;
    }

    public int getZ()
    {
        return this.Z;
    }

    public Vector Add(Vector other)
    {
        return new Vector(this.X+other.getX(),this.Y+other.getY() , this.Z+other.getZ());
    }

    public int dotProduct(Vector other)
    {
        return (this.X*other.getX()+this.Y*other.getY()+this.Z*other.getZ());
    }

    public Vector crossProduct(Vector other)
    {
        int x = this.Y*other.getZ()-this.Z*other.getY();
        int y = this.Z*other.getX()-this.X*other.getZ();
        int z = this.X*other.getY()-this.Y*other.getX();
        return new Vector(x,y,z);
    }
    public Vector scalarProduct(int scalar)
    {
        return new Vector(this.X*scalar,this.Y*scalar,this.Z*scalar);
    }

}
