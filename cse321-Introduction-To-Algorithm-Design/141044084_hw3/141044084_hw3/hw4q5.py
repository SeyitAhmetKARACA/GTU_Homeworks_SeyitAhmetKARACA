"""
f given string : “Tobeornottobe”,
The pattern holds A for “to”, B for “be”, C for “or”, D for “not”; hence, the
pattern is ABCDAB.
"""
import string

def q5(_string,_patternList):
	check = False
	pattern = ""
	for i in _patternList:
		if i.lower() in _string[0:len(i)].lower():
			pattern += string.ascii_uppercase[_patternList.index(i)]
			return pattern + q5(_string[len(i):],_patternList)
			check = True
			break
	if check == False:
		return pattern

word = "ToBeornottoBe"
word2 = "aliatabakatabakali"
patternList = ["to","be","or","not"]
patternList2 = ["ali","ata","bak"]
pattern = "ABCDAB"
pattern2 = "ABCBCA"

if pattern2 == q5(word2,patternList2):
	print(pattern2,"is valid on",word2)
else:
	print(pattern2,"is NOT valid on",word2)















