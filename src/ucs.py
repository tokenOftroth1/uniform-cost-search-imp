from collections import defaultdict
from queue import PriorityQueue
import pandas as pd

class Graph:
    def __init__(self,directed):

        self.graph = defaultdict(list)
        self.directed = directed

    def add_edge(self,u,v,weight):

        if self.directed:
            value=(weight,v)
            self.graph[u].append(value)

        else:
            value = (v,weight)
            self.graph[u].append(value)
            value=(u,weight)
            self.graph[v].append(value)

    def ucs(self,current_node,goal_node):

        visited=[]
        queue=PriorityQueue()
        queue.put((0,current_node))
        
        while not queue.empty():
            item=queue.get()
            current_node=item[1]
            
            if current_node == goal_node:
                print(current_node,end=" ")
                queue.queue.clear()
            else:
                if current_node in visited:
                    continue
                print(current_node,end=" ")
                visited.append(current_node)
                
                for neighbor in self.graph[current_node]:
                    queue.put((neighbor[0],neighbor[1]))
def is_All_cost_number(cost_list):
    for c in cost_list:
        if( not (type(c).__name__== "int" or type(c).__name__== "float")):
            return False
    return True
def is_equal(from_list,to_list,cost_list):
    for item in from_list:
        if(pd.isna(item)):
            return False
    for item in to_list:
        if(pd.isna(item)):
            return False
    for item in cost_list:
        if(pd.isna(item)):
            return False

    return True
def validation(from_list,to_list,cost_list):
    return is_equal(from_list,to_list,cost_list) and is_All_cost_number(cost_list)




try:
    data = pd.read_excel("Data\data.xlsx")
    from_list=data['from'].tolist()
    to_list=data['To'].tolist()
    cost_list=data['Cost'].tolist()
    #print(from_list,to_list,cost_list)

    if(not validation(from_list,to_list,cost_list)) :
        x=1/0
    g=Graph(True)
    for i in range(len(from_list)):
        g.add_edge(from_list[i],to_list[i],cost_list[i])
    print(g.graph)
    S=str(input('Enter your Starting point'))
    G=str(input('Enter your Goal point'))
    g.ucs(S.upper(),G.upper())

 
except:
    print("inappropriate data of size")





