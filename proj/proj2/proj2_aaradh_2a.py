from math import inf
import random
import time
import numpy as np
import pickle
import networkx
import matplotlib.pyplot as plt

class PriorityQueue:
    def __init__(self):
        self.queue = []
        self.pos = []

    def insert(self, vertex, d):
        self.queue.append((vertex, d))
        self.pos.append(len(self.queue) - 1)

    def update(self, vertex, d):
        self.queue[self.get_pos(vertex)] = (vertex, d)
    
    def isEmpty(self):
        if len(self.queue):
            return False
        return True
    
    def get_pos(self, vertex):
        return self.pos[vertex]

    def get_cheapest(self):
        min = inf
        min_ind = 0

        for i in range(len(self.queue)):
            if self.queue[i][1] < min:
                min = self.queue[i][1]
                min_ind = i
        
        cheapest = self.queue[min_ind]

        for i in range(min_ind, len(self.queue)):
            self.pos[i] -= 1

        self.queue.pop(min_ind)

        return cheapest


class Graph:
    def __init__(self, num_V):
        self.num_V = num_V
        self.num_E = 0
        self.matrix = [[0]*num_V]*num_V


    def add_edge(self, vertex_a, vertex_b, weight):
        self.matrix[vertex_a][vertex_b] = weight
        self.num_E += 1
    
    def dijkstra(self, source):
        d = [inf]*self.num_V
        pi = [None]*self.num_V
        S = [0]*self.num_V

        pq = PriorityQueue()

        d[source] = 0

        for i in range(self.num_V):
            pq.insert(i, d[i])
        
        while not pq.isEmpty():
            u = pq.get_cheapest()[0]
            S[u] = 1

            for i in range(len(self.matrix[u])):
                if self.matrix[u][i] != 0 and S[i] != 1 and d[i] > d[u] + self.matrix[u][i]:
                    d[i] = d[u] + self.matrix[u][i]
                    pi[i] = u
                    pq.update(i, d[i])
        
        return d, pi

    def print_shortest_path(self, d, pi, source):
        print(f"Source: {source}")
        for i in range(len(pi)):
            path = []
            j = i
            while pi[j] != None:
                path.append(pi[j])
                j = pi[j]
            
            path.reverse()
            for v in path:
                print(f"{v}->", end="")
            print(f"{i} (distance: {d[i]})")

def main():

    graph_sizes = [
        5,
        10,
        25,
        50,
        75,
        100,
        250,
        500,
        750,
        1000,
        1100,
        1250,
        1500,
        1750,
        2000,
        2100,
        2250,
        2500,
        2750,
        3000
    ]

    edge_densities = np.arange(0.1, 1, 0.2)

    results = {}

    random.seed(200120011002)

    for edge_density in edge_densities:
        results[edge_density] = []

        for i in [10]:
            G = Graph(i)
            g = networkx.gnp_random_graph(i, edge_density, directed=True)
            while not networkx.is_strongly_connected(g):
                g = networkx.gnp_random_graph(i, edge_density, directed=True)
            for s, d, w in g.edges(data=True):
                w['weight'] = random.randint(1, 11) 
            #     G.add_edge(s, d, random.randint(1, 10000))
            networkx.draw_networkx(g, pos=networkx.circular_layout(g), node_size=1000, arrowsize=20)
            plt.title(f'Edge Density: {round(edge_density, 1)}')
            plt.savefig(f'p_{round(edge_density, 1)}_graph.png')
            plt.show()

            # source = 0
            # tic = time.process_time_ns()
            # G.dijkstra(source)
            # toc = time.process_time_ns()
            # print(f"Size: {i} \t| Time taken: {toc-tic:,} ns \t| Edge Density = {G.num_E/(G.num_V**2)}")

            # results[edge_density].append((i, toc-tic))

    print(results)
    
    # with open('2a.pickle', 'wb') as pk_file:
    #     pickle.dump(results, pk_file)        

if __name__ == '__main__':
    main()