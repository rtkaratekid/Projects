all: main

main: main.cpp
	clang++ -Wall -std=c++11 main.cpp -o main

test: main
	chmod +x test.sh

cover:
    clang++ -fprofile-instr-generate -fcoverage-mapping main.cpp -o main
    LLVM_PROFILE_FILE=“main.profraw” ./main < test.txt
    xcrun llvm-profdata merge -sparse main.profraw -o main.profdata
    xcrun llvm-cov show ./main -instr-profile=main.profdata

clean:
	rm -f main
	rm -f main.profdata main.profraw