//
//  main.cpp
//  assignment3
//
//  Created by Tristan Mayfield on 1/15/19.
//  Copyright © 2019 Tristan Mayfield. All rights reserved.
//

#include <cmath>
#include <iostream>
#include <iterator> //for std::istream_iterator
#include <sstream>  //for std::istringstream
#include <string>
#include <vector> //for std::vector

// two assertions so far
// to store the coordinates easily
struct Quadrilateral {
  double x1, y1, x2, y2, x3, y3;
  double len01, len12, len23, len30;
  double slope01, slope12, slope23, slope30;
  std::vector<std::string> tokens;

public:
  void resetValues() {
    x1 = 0;
    x2 = 0;
    x3 = 0;
    y1 = 0;
    y2 = 0;
    y3 = 0;
    len01 = 0;
    len12 = 0;
    len23 = 0;
    len30 = 0;
    slope01 = 0;
    slope12 = 0;
    slope23 = 0;
    slope30 = 0;
  }
};

// to find the distance between two points
double length(double x0, double y0, double x1, double y1) {
  return sqrt(pow(x1 - x0, 2) + pow(y1 - y0, 2));
}

// to find the slope of a line with two points
double slope(double x0, double y0, double x1, double y1) {
  if (x0 == x1) {
    return (y1 - y0) / (x1 - x0);
    // return NULL;
  }
  return (y1 - y0) / (x1 - x0);
}

/*
 "error 2" -- if any two points coincide
 "error 3" -- if any two line segments representing sides cross each other
 "error 4" -- if any three points are colinear
 */

bool hasInvalidPoints(Quadrilateral &q) {
  // to check for the correct number of inputs
  if (q.tokens.size() != 6) {
    return true;
  }

  // checking the ascii to make sure each char is an int
  for (std::string s : q.tokens) {
    for (char c : s) {
      if (c < 48 || c > 57) {
        return true;
      }
    }
  }

  q.x1 = std::stod(q.tokens[0]);
  q.y1 = std::stod(q.tokens[1]);
  q.x2 = std::stod(q.tokens[2]);
  q.y2 = std::stod(q.tokens[3]);
  q.x3 = std::stod(q.tokens[4]);
  q.y3 = std::stod(q.tokens[5]);

  // check if coordinates are outside range
  if (100 < q.x1 || 0 > q.x1) {
    return true;
  } else if (100 < q.x2 || 0 > q.x2) {
    return true;
  } else if (100 < q.x3 || 0 > q.x3) {
    return true;
  } else if (100 < q.y1 || 0 > q.y1) {
    return true;
  } else if (100 < q.y2 || 0 > q.y2) {
    return true;
  } else if (100 < q.y3 || 0 > q.y3) {
    return true;
  }
  return false;
}

// to see if any of the points are the same
bool pointsCoincide(Quadrilateral q) {
  if (q.x1 == q.x2 && q.y1 == q.y2) {
    return true;
  } else if (q.x2 == q.x3 && q.y2 == q.y3) {
    return true;
  } else if (q.x1 == q.x3 && q.y1 == q.y3) {
    return true;
  } else if (q.x1 == 0 && q.y1 == 0) {
    return true;
  } else if (q.x2 == 0 && q.y2 == 0) {
    return true;
  } else if (q.x3 == 0 && q.y3 == 0) {
    return true;
  }
  return false;
}

// See https://www.geeksforgeeks.org/orientation-3-ordered-points/
// To find orientation of three points
// The function returns following values
// return 0 if colinear, 1 if Clockwise, and 2 if Counterclockwise
int orientation(int x1, int y1, int x2, int y2, int x3, int y3) {

  int first = (y2 - y1);
  int second = (x3 - x2);
  int third = (x2 - x1);
  int fourth = (y3 - y2);
  int fifth = first * second;
  int sixth = third * fourth;
  int val = fifth - sixth;

  if (val == 0)
    return 0; // colinear

  return (val > 0) ? 1 : 2; // clock or counterclock wise
}

bool linesCross(Quadrilateral q) {
  /*
   Two segments (p1,q1) and (p2,q2) intersect if and only if one of the
   following two conditions is verified
   1. General Case:
   – (p1, q1, p2) and (p1, q1, q2) have different orientations and
   – (p2, q2, p1) and (p2, q2, q1) have different orientations.
   */

  int orient1 = orientation(q.x1, q.y1, q.x2, q.y2, q.x3, q.y3);
  int orient2 = orientation(q.x1, q.x1, q.x2, q.y2, 0, 0);
  int orient3 = orientation(q.x3, q.y3, 0, 0, q.x1, q.y1);
  int orient4 = orientation(q.x3, q.y3, 0, 0, q.x2, q.y2);

  // check line 1 and 3
  if ((orientation(0, 0, q.x1, q.y1, q.x2, q.y2) !=
       orientation(0, 0, q.x1, q.y1, q.x3, q.y3)) &&
      (orientation(q.x2, q.y2, q.x3, q.y3, 0, 0) !=
       orientation(q.x2, q.x2, q.x3, q.y3, q.x1, q.y1))) {
    return true;
    // check lines 2 and 4
  } else if ((orient1 != orient2) && (orient3 != orient4)) {
    return true;
  }

  return false;
}

bool hasColinearity(Quadrilateral q) {

  // check for colinearity by checking slopes
  if (slope(0, 0, q.x2, q.y2) == slope(0, 0, q.x1, q.y1)) {
    return true;
  } else if (slope(q.x1, q.y1, q.x3, q.y3) == slope(q.x1, q.y1, q.x2, q.y2)) {
    return true;
  } else if (slope(0, 0, q.x2, q.y2) == slope(0, 0, q.x3, q.y3)) {
    return true;
  }

  return false;
}

bool hasValidInputs(Quadrilateral &q) {
  if (hasInvalidPoints(q)) {
    std::cout << "error 1\n";
    return false;
  } else if (pointsCoincide(q)) {
    std::cout << "error 2\n";
    return false;
  } else if (linesCross(q)) {
    std::cout << "error 3\n";
    return false;
  } else if (hasColinearity(q)) {
    std::cout << "error 4\n";
    return false;
  }

  return true;
}

// to determine if a quadrilateral is a square
bool isSquare(Quadrilateral q) {
  assert(hasValidInputs(q)); // assertion oracle that is unnecessary
  if (q.x1 == q.x2 && q.x1 == q.y2 && q.x1 == q.y3 && q.y1 == q.x3) {
    if (q.y1 == 0 && q.x3 == 0) {
      return true;
    }
  }
  return false;
}

// to determine if a quadrilateral is a rectangle
bool isRectangle(Quadrilateral q) {

  // working with points should filter out a rhombus
  if (q.x1 == q.x2 && q.y1 == q.x3 && q.y2 == q.y3) {
    if (q.y1 == 0 && q.x3 == 0) {
      return true;
    }
  }
  return false;
}

// to determine if a quadrilateral is a rhombus
bool isRhombus(Quadrilateral &q) {

  // calculating all the lengths and storing data for future comparisons if
  // necessary
  q.len01 = length(0, 0, q.x1, q.y1);
  q.len12 = length(q.x1, q.y1, q.x2, q.y2);
  q.len23 = length(q.x2, q.y2, q.x3, q.y3);
  q.len30 = length(q.x3, q.y3, 0, 0);

  if (q.len01 == q.len12 && q.len12 == q.len23 && q.len23 == q.len30) {
    return true;
  }
  return false;
}

// to determine if it's a parallelogram
bool isParallelogram(Quadrilateral &q) {
  q.slope01 = slope(0, 0, q.x1, q.y1);
  q.slope12 = slope(q.x1, q.y1, q.x2, q.y2);
  q.slope23 = slope(q.x2, q.y2, q.x3, q.y3);
  q.slope30 = slope(q.x3, q.y3, 0, 0);
  if ((q.slope01 == q.slope23 && q.slope12 == q.slope30) &&
      (q.len01 == q.len23 && q.len12 == q.len30) && (q.slope01 != q.slope30)) {
    return true;
  }
  return false;
}

// to determine if it's a trapezoid
bool isTrapezoid(Quadrilateral q) {
  if ((q.slope01 == q.slope23) && (q.slope12 != q.slope30)) {
    return true;
    // if top and bottom are parallel and left and right are not
  } else if ((q.slope12 == q.slope30) && (q.slope01 != q.slope23)) {
    return true;
  }
  return false;
}

bool isKite(Quadrilateral q) {
  // left and right vertices and adjacent sides
  if (q.len01 == q.len30 && q.len12 == q.len23) {
    return true;
    // top and  bottom vertices and adjacent sides
  } else if (q.len01 == q.len12 && q.len23 == q.len30) {
    return true;
  }
  return false;
}

void typeOfQuadrilateral(Quadrilateral q) {
  if (!hasValidInputs(q)) {
    exit(0);
  }
  if (isSquare(q)) {
    std::cout << "square\n";
    return;
  } else if (isRectangle(q)) {
    std::cout << "rectangle\n";
    return;
  } else if (isRhombus(q)) {
    std::cout << "rhombus\n";
    return;
  } else if (isParallelogram(q)) {
    std::cout << "parallelogram\n";
    return;
  } else if (isKite(q)) {
    std::cout << "kite\n";
    return;
  } else if (isTrapezoid(q)) {
    std::cout << "trapezoid\n";
    return;
  }
  std::cout << "quadrilateral\n";
}

int main(int argc, const char *argv[]) {
  // hopefully this is saying: while we are not at the end of the file, run this
  // chunk of code
  while (!std::cin.eof()) {
    // quad should maybe initialized
    // yes you can argue that it's checked later, but containing that check
    // right at the get-go will save a lot of trouble
    Quadrilateral quad;
    std::string input = "";

    std::getline(std::cin, input);
    assert(input != ""); // assertion oracle
    std::istringstream ss(input);

    std::istream_iterator<std::string> begin(ss), end;

    // putting all the tokens in the vector
    std::vector<std::string> arrayTokens(begin, end);
    quad.tokens = arrayTokens;

    typeOfQuadrilateral(quad);
    quad.resetValues();
  }
  return 0;
}
