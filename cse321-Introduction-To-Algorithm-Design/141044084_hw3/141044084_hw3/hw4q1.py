import pandas as pd
dt = pd.read_excel("Graph_data.XLS")
g = dt.values.tolist()
root = list(dt)[1]
g = g[2:]
keys = []
values = []
for item in g:
	if item[0] not in keys:
		keys.append(item[0])
		temp = []
		temp.append(item[1])
		values.append(temp)
	else:
		index = keys.index(item[0])
		values[index].append(item[1])


def dfs(graph, start,output=None,visited=None):
	if visited is None:
		visited = set()
	if output is None:
		output = []
	if (start in visited) == False:
		output.append(start)
	visited.add(start)
	if start in graph:
		for next in graph[start] - visited:
			dfs(graph, next, output,visited)
	return output

def bfs(graph, start,queue=None ,output=None,visited=None):
	if visited is None:
		visited = set()
	if queue is None:
		queue = []
	if output is None:
		output = []
	if (start in visited) == False:
		queue.insert(0,start)
		visited.add(start)
		v = queue.pop(0)
		
		output.append(v)
		if v in graph:
			for next in graph[v] - visited:
				queue.append(next)
	if len(queue) > 0:
		v = queue.pop(0)
		bfs(graph, v, queue,output,visited)
	return output

def printFormated(liste):
	for i in range(0,len(liste)):
		if i == len(liste)-1:
			print(liste[i])
		else:
			print(liste[i],"-> ",end='')

graph = {}
i = 0 
for i in range(0,len(keys)):
	loopSet = set()
	for item in values[i]:
		loopSet.add(item)
	graph[keys[i]] = loopSet


printFormated(bfs(graph, root))
printFormated(dfs(graph, root))





