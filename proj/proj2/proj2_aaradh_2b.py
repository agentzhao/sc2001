from math import inf
import random
import time
import numpy as np
import pickle
import networkx

class HeapNode:
    def __init__(self, vertex, d):
        self.vertex = vertex
        self.d = d

class MinHeap:
    def __init__(self, max_size):
        self.max_size = max_size
        self.min_heap = [HeapNode(-1, 0)]*self.max_size
        self.size = 0
        self.pos = [None]*self.max_size

    def swap(self, a_ind, b_ind):
        self.min_heap[a_ind], self.min_heap[b_ind] = \
        self.min_heap[b_ind], self.min_heap[a_ind]

        self.pos[self.min_heap[a_ind].vertex], self.pos[self.min_heap[b_ind].vertex] = \
        self.pos[self.min_heap[b_ind].vertex], self.pos[self.min_heap[a_ind].vertex]

    def root(self, index):
        if index == 0:
            return 0
        return (index-1)//2

    def left_child(self, index):
        return 2*index+1
    
    def right_child(self, index):
        return 2*index+2

    def is_leaf(self, index):
        return 2*index+1 > self.size-1
    
    def heapify(self, index):
        if not self.is_leaf(index):
            if self.min_heap[index].d > self.min_heap[self.left_child(index)].d or \
                self.min_heap[index].d > self.min_heap[self.right_child(index)].d:

                if self.min_heap[self.left_child(index)].d < self.min_heap[self.right_child(index)].d:
                    self.swap(index, self.left_child(index))
                    self.heapify(self.left_child(index))
                else:
                    self.swap(index, self.right_child(index))
                    self.heapify(self.right_child(index))
        
    def insert(self, vertex, d):
        self.size += 1
        self.min_heap[self.size-1] = HeapNode(vertex, d)
        self.pos[vertex] = self.size-1

        current = self.size-1
        while self.min_heap[current].d < self.min_heap[self.root(current)].d:
            self.swap(current, self.root(current))
            current = self.root(current)

    def get_cheapest(self):
        cheapest = self.min_heap[0]
        self.min_heap[0] = self.min_heap[self.size-1]
        self.pos[self.min_heap[self.size-1].vertex] = 0
        self.size -= 1
        self.heapify(0)

        return cheapest

    def rearrange(self, vertex, d):        
        self.min_heap[self.pos[vertex]] = self.min_heap[self.size-1]
        self.size -= 1
        
        self.heapify(self.pos[vertex])

        self.insert(vertex, d)
    
    def is_empty(self):
        if self.size > 0:
            return False
        return True

class GraphNode:
    def __init__(self, value, weight):
        self.value = value
        self.weight = weight
        self.next = None

class Graph:
    def __init__(self, num_V):
        self.num_V = num_V
        self.num_E = 0
        self.adj_list = [None]*num_V

    def add_edge(self, vertex_a, vertex_b, weight):
        node = GraphNode(vertex_b, weight)
        node.next = self.adj_list[vertex_a]
        self.adj_list[vertex_a] = node
        self.num_E += 1
    
    def get_vertex_degree(self, vertex):
        current = self.adj_list[vertex]
        count = 0

        while current != None:
            current = current.next
            count += 1
        
        return count
    
    def dijkstra(self, source):
        d = [inf]*self.num_V
        pi = [None]*self.num_V
        S = [0]*self.num_V

        pq = MinHeap(self.num_V)

        d[source] = 0

        for i in range(self.num_V):
            pq.insert(i, d[i])
        
        while not pq.is_empty():
            u = pq.get_cheapest().vertex
            S[u] = 1

            current = self.adj_list[u]
            while current != None:
                if S[current.value] != 1 and d[current.value] > d[u] + current.weight:
                    d[current.value] = d[u] + current.weight
                    pi[current.value] = u
                    pq.rearrange(current.value, d[current.value])
                current = current.next

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

        for i in graph_sizes:
            G = Graph(i)
            g = networkx.gnp_random_graph(i, edge_density, directed=True)
            while not networkx.is_strongly_connected(g):
                g = networkx.gnp_random_graph(i, edge_density, directed=True)
            for edge in g.edges():
                s, d = edge
                G.add_edge(s, d, random.randint(1, 10000))

            source = 0
            tic = time.process_time_ns()
            G.dijkstra(source)
            toc = time.process_time_ns()
            print(f"Size: {i} \t| Time taken: {toc-tic:,} ns \t| Edge Density = {G.num_E/(G.num_V**2)}")

            results[edge_density].append((i, toc-tic))

    print(results)
    
    with open('2b.pickle', 'wb') as pk_file:
        pickle.dump(results, pk_file)            

if __name__ == '__main__':
    main()