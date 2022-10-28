#!/bin/bash

red='\033[0;31m';
green='\033[0;32m';
yellow='\033[1;33m';
noColor='\033[0m';

file1=$1;
file2=$2;
args=$3;
improved=0;
withAllPairs=0;
assumption=0;
show=0;
result="";

printResult() {
	if [ "${result}" == "true" ]
	then
		echo -e "${green}${result}${noColor}";
	else
		echo -e "${red}${result}${noColor}";
	fi
}

showLts_t() {
	./source/ltsgraph out/lts_t_files/system1.aut & ./source/ltsgraph out/lts_t_files/system2.aut
}

# actuall staps:
echo "Checking if two mcrl2 files exits...";
if [ $# -eq 3 ]
then
	if [[ "$args" == *"i"* ]];
	then
		improved=1;
	fi
	if [[ "$args" == *"p"* ]];
	then
		withAllPairs=1;
	fi
	if [[ "$args" == *"a"* ]];
	then
		assumption=1;
	fi
	if [[ "$args" == *"s"* ]];
	then
		show=1;
	fi
fi

if [ $# -ge 2 ]
then
	if [ -f "$file1" -a -f "$file2" ]
	then
		if [[ "$file1" == *".mcrl2" && "$file2" == *".mcrl2" ]]
		then
			echo -e "${green}Files exixts.${noColor}";
			echo "Transforming the files into lts_t...";
			mkdir -p out;
			mkdir -p out/lps_files;
			./source/mcrl22lps "${file1}" > out/lps_files/system1.lps;
			./source/mcrl22lps "${file2}" > out/lps_files/system2.lps;

			mkdir -p out/lts_t_files;
			./source/lps2lts out/lps_files/system1.lps out/lts_t_files/system1.aut;
			./source/lps2lts out/lps_files/system2.lps out/lts_t_files/system2.aut;


			echo "Transforming lts_t into lts_s...";
			mkdir -p out/lts_s_files;
			if [ "${withAllPairs}" -eq 1 ]
			then
				echo "comparing the systems of reactive bisimilarity unsing strong bisimilarity definition...";
			fi
			java -jar source/lts_t2lts.jar out/lts_t_files/system1.aut out/lts_t_files/system2.aut "$improved" "$withAllPairs" "$assumption";

			if [ "${withAllPairs}" -eq 0 ]
			then
				echo "comparing the systems of reactive bisimilarity unsing mCRL2...";
				./source/ltscompare -ebisim -q out/lts_s_files/system1.aut out/lts_s_files/system2.aut > out/result.txt;
				result=$(head -n 1 "out/result.txt");
				printResult;
			fi

			if [ "$show" -eq 1 ]
			then
				showLts_t
			fi
			rm -r out/;
		else
			echo -e "${red}$One or both files does not have the extension .mcrl2${noColor}";
		fi
	else
		echo -e "${red}${file1} or ${file2} does not exixts!${noColor}";
	fi
else
    echo -e "${red}Please enter the names of two mcrl2 files, that should be tested of reactive bisimilarity!${noColor}";
fi
