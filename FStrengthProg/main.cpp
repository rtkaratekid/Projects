//
//  main.cpp
//  HangboardProgression
//
//  Created by Tristan Mayfield on 10/19/18.
//  Copyright © 2018 Tristan Mayfield. All rights reserved.
//

#include <iostream>
#include <fstream>
using namespace std;


void writeAndPrintProgress(fstream &output,
                           std::tm* now,
                           double estMax){
    if(output.is_open()){
        output << (now->tm_year + 1900) << '-'
        << (now->tm_mon + 1) << '-'
        <<  now->tm_mday << ", "
        << estMax << endl;
    }else{
        std::cout << "The file stream isn't open" << endl;
    }
}

int main(int argc, const char * argv[]) {
    /*
     clang++ -std=c++11 -c main.cpp
     This compiles main.cpp into the file main.o (check this with ls to confirm).  The -std=c++11 part lets us use some things that C++ added in 2011.  The -c means "compile"
     now, we're going to link the compiled files together (in this case there's actually only 1), again using clang:
     clang++ -o main main.o
     This will create a file called main which is the executable program.  The "-o main" part means "name the output main" Run your executable program with the following:
     ./main
     the ./ part just means "use the program called main in this folder"
     Try passing commandline arguments to your program with something like:
     ./main these are command line argument
     */
    double weight, reps;
    cout << "\nData for 10 second, two-arm hangs";
    cout << "\nTotal Weight: ";
    cin >> weight;
    cout << "Number of reps: ";
    cin >> reps;
    
    //import a txt file to write the data to
    fstream progressionTxt;
    progressionTxt.open("progression.txt", ios::app);
   
    //calculate the estimated max
    double estimatedMax = ((weight * reps * .0333) + weight);
    
    //taken from stack overflow, don't quite understand
    //https://stackoverflow.com/questions/997946/how-to-get-current-time-and-date-in-c
    std::time_t t = std::time(0);   // get time now
    std::tm* now = std::localtime(&t);
    std::cout << (now->tm_year + 1900) << '-'
    << (now->tm_mon + 1) << '-'
    <<  now->tm_mday
    << ", ";
    
    //write to a txt file in format: date, estMax
//    progressionTxt << (now->tm_year + 1900) << '-'
//    << (now->tm_mon + 1) << '-'
//    <<  now->tm_mday << ", "
//    << estimatedMax << endl;
    
    writeAndPrintProgress(progressionTxt, now, estimatedMax);
    
     cout << "Estimated max 10 second hang: " << estimatedMax << "\n";
    
    progressionTxt.close();
    
    return 0;
}


