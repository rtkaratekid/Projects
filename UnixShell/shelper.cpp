//
// Created by Tristan Mayfield on 2019-02-04.
//

#include "shelper.h"

/*
  text handling functions
 */

bool splitOnSymbol(std::vector<std::string>& words, int i, char c){
    if(words[i].size() < 2){ return false; }
    int pos;
    if((pos = words[i].find(c)) != std::string::npos){
        if(pos == 0){
            //starts with symbol
            words.insert(words.begin() + i + 1, words[i].substr(1, words[i].size() -1));
            words[i] = words[i].substr(0,1);
        } else {
            //symbol in middle or end
            words.insert(words.begin() + i + 1, std::string{c});
            std::string after = words[i].substr(pos + 1, words[i].size() - pos - 1);
            if(!after.empty()){
                words.insert(words.begin() + i + 2, after);
            }
            words[i] = words[i].substr(0, pos);
        }
        return true;
    } else {
        return false;
    }

}

// splitting all the tokens into a vector of strings
std::vector<std::string> tokenize(const std::string& s){

    std::vector<std::string> ret;
    int pos = 0;
    int space;
    //split on spaces
    while((space = s.find(' ', pos)) != std::string::npos){
        std::string word = s.substr(pos, space - pos);
        if(!word.empty()){
            ret.push_back(word);
        }
        pos = space + 1;
    }

    std::string lastWord = s.substr(pos, s.size() - pos);
    if(!lastWord.empty()){
        ret.push_back(lastWord);
    }

    for(int i = 0; i < ret.size(); ++i){
        for(auto c : {'&', '<', '>', '|'}){
            if(splitOnSymbol(ret, i, c)){
                --i;
                break;
            }
        }
    }

    return ret;

}

// for printing out the commands and their member variables
std::ostream& operator<<(std::ostream& outs, const Command& c){
    outs << c.exec << " argv: ";
    for(const auto& arg : c.argv){ if(arg) {outs << arg << ' ';}}
    outs << "fds: " << c.fdStdin << ' ' << c.fdStdout << ' ' << (c.background ? "background" : "");
    return outs;
}

//returns an empty vector on error
std::vector<Command> getCommands(const std::vector<std::string>& tokens){
    std::vector<Command> ret(std::count(tokens.begin(), tokens.end(), "|") + 1);  //1 + num |'s commands

    int first = 0;
    int last = std::find(tokens.begin(), tokens.end(), "|") - tokens.begin();
    int filedes[2];
    bool error = false;
    for(int i = 0; i < ret.size(); ++i){
        if((tokens[first] == "&") || (tokens[first] == "<") ||
           (tokens[first] == ">") || (tokens[first] == "|")){
            error = true;
            break;
        }

        ret[i].exec = tokens[first];
        ret[i].argv.push_back(tokens[first].c_str()); //argv0 = program name
        // std::cout << "exec start: " << ret[i].exec << std::endl;
        ret[i].fdStdin = 0;
        ret[i].fdStdout = 1;
        ret[i].background = false;

        if(ret[i].exec.find('=') != -1){ // there's an '=' in the first string
            break; // break out of the loop and let main handle it
        }

        for(int j = first + 1; j < last; ++j){
            if(tokens[j] == ">" || tokens[j] == "<" ){
                //I/O redirection

                // input file: set fd to the filename provided
                if(tokens[j] == "<"){
                    ret[i].fdStdin = open(tokens[j+1].data(), O_RDONLY);
                    if(ret[i].fdStdin < 0){
                        std::cout<<"Error opening the file to read from\n";
                    }
                }

                // output file: set fd to the filename provided
                if(tokens[j] == ">"){
                    ret[i].fdStdout = open(tokens[j+1].data(), O_CREAT | O_WRONLY, 0666);
                    if(ret[i].fdStdout < 0){
                        std::cout<<"Error opening the file to write to\n";
                        std::cout<<strerror(errno)<<"_________________________________!!\n";
                    }

                    // adding nullptr to signal to exec the end of the args
                    ret[i].argv.push_back(nullptr);
                }



            } else if(tokens[j] == "&"){
                //Fill this in if you choose to do the optional "background command" part
                assert(false);
            } else {
                //otherwise this is a normal command line argument!
                ret[i].argv.push_back(tokens[j].c_str());
            }
        }

        if(i > 0){

            // intitialize pipe
            int pp = pipe(filedes);
            if(pp != 0){ std::cout << "error creating pipe\n"; }

            // set the out for the last command to the write end of the pipe
            ret[i-1].fdStdout = filedes[1];
            ret[i].fdStdin = filedes[0];

        }
        //exec wants argv to have a nullptr at the end!
        ret[i].argv.push_back(nullptr);

        //find the next pipe character
        first = last + 1;
        if(first < tokens.size()){
            last = std::find(tokens.begin() + first, tokens.end(), "|") - tokens.begin();
        }
    }

    if(error){
        close(filedes[0]);
        close(filedes[1]);
    }

    return ret;
}