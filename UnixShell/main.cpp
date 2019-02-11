#include <iostream>
#include "shelper.h"
#include <ctime>
#include <chrono>

//extern "C" {
//    #include <readline/readline.h>
//}

// The start of getting tab completion- getting linker errors
//https://thoughtbot.com/blog/tab-completion-in-gnu-readline 
//
//char **command_completion(const char *, int, int);
//char *command_generator(const char *, int);
//
//char *commands[] = {
//        "Arthur Dent",
//        "Ford Prefect",
//        "Tricia McMillan",
//        "Zaphod Beeblebrox",
//        NULL
//};

int main() {
    std::cout<<"\nW E L C O M E  T O  T S H E L L\n"<<std::endl;

    std::string cmnd;
    auto timenow = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
    std::string time = ctime(&timenow);
    for(int i = 11; i <= 19; ++i){
        std::cout<<time[i];
    }
    std::cout<< system("pwd") <<" ~> ";

    while(getline(std::cin, cmnd)){

//        More tab completion stuff
//        char *buffer = readline("");
//        //const char *buffer = cmnd.data();
//        if (buffer) {
//            std::cout<<"You entered: "<< buffer<<"\n";
//        }


        std::vector<std::string> tokens = tokenize(cmnd);
        std::vector<Command> commands = getCommands(tokens);
        std::vector<pid_t> pids;


        // checking to see if any of the tokens are environmental variables, and changing them if they are
        for(std::string &s : tokens){
            if(s[0] == '$'){
                std::string variable = getenv(s.substr(1, s.size()).data());
                s = variable;
            }
        }

    // for each command, fork() and exec()
        for(Command c : commands) {
            int dex = c.exec.find('=');
            if(dex != -1){ // there's an '=' in the first string
                // split the string at '=' index
                std::string name = c.exec.substr(0, dex);
                std::string value = c.exec.substr(dex+1, dex);
                std::cout<<name<<" "<<value<<"\n";

                setenv(name.data(), value.data(), 1); // call setenv with ability to overwrite
                break; // break out of the loop cause we're done here
            }






            if(c.exec == "cd"){
                if(c.argv.size() == 2){
                    // no arguments, go home
                    int change = chdir(getenv("HOME"));
                    if(change != 0){
                        std::cout<<"chdir to home didn't work\n";
                    }
                } else {
                    // follow the file path
                    int change = chdir(c.argv[1]);
                    if(change != 0){
                        std::cout<<"chdir to dir didn't work\n";
                    }
                }
                // I want to exit out of the for each and do the next while loop
                break;
            }

            pid_t pid = fork();

            if (pid == -1) {
                std::cout << "fork failed " << std::endl;
                break;

            } else if (pid == 0) {
                // execute in child

                int fdIn = dup2(c.fdStdin, 0);
                if (fdIn == -1) {
                    std::cout << "dup2() input failed\n";
                    exit(1);
                }

                int fdOut = dup2(c.fdStdout, 1);
                if (fdOut == -1) {
                    std::cout << "dup2() output failed\n";
                    exit(1);
                }

                // if command isn't zero or one close file descriptors
                for(Command c : commands){
                    if(c.fdStdin != 0){
                        close(c.fdStdin);
                    }
                    if(c.fdStdout != 1){
                        close(c.fdStdout);
                    }
                }

                int ret = execvp(c.argv[0], (char *const *) c.argv.data());
                if (ret == -1) {
                    std::clog << "execvp() failed: " << strerror(errno) << std::endl;
                    exit(1);
                }
                // finished with child, exit
                exit(1);
            } else {
                // we're in parent, make sure to store the pid_t's
                pids.push_back(pid);
            }

        }

        for(Command c : commands){
            if(c.fdStdin != 0){
                close(c.fdStdin);
            }
            if(c.fdStdout != 1){
                close(c.fdStdout);
            }
        }

        // wait on all the child processes here?
        int returnStatus;
        for(pid_t p : pids){
            waitpid(p, &returnStatus, 0);
        }
        auto timenow = std::chrono::system_clock::to_time_t(std::chrono::system_clock::now());
        std::string time = ctime(&timenow);
        std::cout<<"\n";
        for(int i = 11; i <= 19; ++i){
            std::cout<<time[i];
        }

        std::cout<< system("pwd")<<" ~> ";

    }

    return 0;
}