//
//  main.cpp
//  assignment3
//
//  Created by Tristan Mayfield on 1/15/19.
//  Copyright © 2019 Tristan Mayfield. All rights reserved.
//

#include <iostream>
#include <string>
#include <cmath>

// to store the coordinates easily
struct Quadrilateral{
    double x1, y1, x2, y2, x3, y3;
    double len01, len12, len23, len30;
    double slope01, slope12, slope23, slope30;
public: void resetValues(){
        x1 = 0; x2 = 0; x3 = 0; y1 = 0; y2 = 0;y3 = 0;
        len01 = 0; len12 = 0; len23 = 0; len30 = 0;
        slope01 = 0; slope12 = 0; slope23 = 0; slope30 = 0;
    }
};

// to find the distance between two points
double length(int x0, int y0, int x1, int y1){
    return sqrt(pow(x1 - x0, 2) + pow(y1 - y0, 2));
}

// to find the slope of a line with two points
double slope(int x0, int y0, int x1, int y1){
    if(x0 == x1){
        return NULL;
    }
    return (y1 - y0) / (x1 - x0);
}

// to determine if a quadrilateral is a square
bool isSquare(Quadrilateral q){
    if(q.x1 == q.x2 && q.x1 == q.y2 && q.x1 == q.y3 && q.y1 == q.x3){
        return true;
    }
    return false;
}

// to determine if a quadrilateral is a rectangle
bool isRectangle(Quadrilateral q){
    
    // working with points should filter out a rhombus
    if(q.x1 == q.x2 && q.y1 == q.x3 && q.y2 == q.y3){
        return true;
    }
    return false;
}

// to determine if a quadrilateral is a rhombus
bool isRhombus(Quadrilateral& q){
    
    // calculating all the lengths and storing data for future comparisons if necessary
    q.len01 = length(0, 0, q.x1, q.y1);
    q.len12 = length(q.x1, q.y1, q.x2, q.y2);
    q.len23 = length(q.x2, q.y2, q.x3, q.y3);
    q.len30 = length(q.x3, q.y3, 0, 0);
    
    if(q.len01 == q.len12 &&
       q.len12 == q.len23 &&
       q.len23 == q.len30){
        return true;
    }
    return false;
}

// to determine if it's a parallelogram
bool isParallelogram(Quadrilateral& q){
    q.slope01 = slope(0, 0, q.x1, q.y1);
    q.slope12 = slope(q.x1, q.y1, q.x2, q.y2);
    q.slope23 = slope(q.x2, q.y2, q.x3, q.y3);
    q.slope30 = slope(q.x3, q.y3, 0, 0);
    if((q.slope01 == q.slope23 && q.slope12 == q.slope30) &&
       (q.len01 == q.len23 && q.len12 == q.len30) &&
       (q.slope01 != q.slope30)){
        return true;
    }
    return false;
}

// to determine if it's a trapezoid
bool isTrapezoid(Quadrilateral q){
    
    if((q.slope01 == q.slope23) && (q.slope12 != q.slope30)){
        return true;
        // if top and bottom are parallel and left and right are not
    } else if ((q.slope12 == q.slope30) && (q.slope01 != q.slope23)){
        return true;
    }
    return false;
}

bool isKite(Quadrilateral q){
    // left and right vertices and adjacent sides
    if(q.len01 == q.len30 &&
       q.len12 == q.len23){
        return true;
        // top and  bottom vertices and adjacent sides
    } else if (q.len01 == q.len12 &&
               q.len23 == q.len30){
        return true;
    }
    return false;
}

void typeOfQuadrilateral(Quadrilateral q){
    if(isSquare(q)){
        std::cout << "square\n";
        return;
    } else if(isRectangle(q)){
        std::cout << "rectangle\n";
        return;
    } else if(isRhombus(q)){
        std::cout << "rhombus\n";
        return;
    } else if(isParallelogram(q)){
        std::cout << "parellelogram\n";
        return;
    } else if (isKite(q)){
        std::cout << "kite\n";
        return;
    } else if(isTrapezoid(q)){
        std::cout << "trapezoid\n";
        return;
    }
    std::cout << "quadrilateral\n";
}

int main(int argc, const char * argv[]) {
    // hopefully this is saying: while we are not at the end of the file, run this chunk of code
    while(!std::cin.eof()){
        Quadrilateral quad;
        std::cin >> quad.x1 >> quad.y1 >> quad.x2 >> quad.y2 >> quad.x3 >> quad.y3;
        typeOfQuadrilateral(quad);
        quad.resetValues();
    }
    return 0;
}
