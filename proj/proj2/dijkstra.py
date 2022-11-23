"""
In the Dijkstra’s algorithm, the choice of the input graph representation and the priority queue implementation will affect its time complexity.
(a) Suppose the input graph G = (V, E) is stored in an adjacency matrix and we use an array for the priority queue. Implement the Dijkstra’s algorithm using this
setting and analyze its time complexity with respect to |V| and |E| both theoretically and empirically.
(b) Suppose the input graph G = (V, E) is stored in an array of adjacency lists and we use a minimizing heap for the priority queue. Implement the Dijkstra’s
algorithm using this setting and analyze its time complexity with respect to |V| and |E| both theoretically and empirically.
(c) Compare the two implementations in (a) and (b). Discuss which implementation is better and in what circumstances.
"""

import heapq
import random
import numpy as np
from numpy import Inf
import networkx as nx
from collections import defaultdict
import warnings
import time

warnings.filterwarnings("ignore")
seed = 1234
random.seed(seed)
np.random.seed(seed)


def newAdjacencyMatrix(n, p):
    # n: number of nodes, p: probability of edge
    G = nx.generators.gnp_random_graph(n, p)
    nx.draw(G, with_labels=True)

    A = nx.adjacency_matrix(G).toarray()

    edges = int(sum(sum(A)) / 2)

    randnums = np.random.randint(2, 10, edges)

    # generate weights
    for i in range(n):
        for j in range(n):
            if A[i][j] == 1:
                A[i][j] = A[j][i] = randnums[0]
                randnums = np.delete(randnums, 0)
    return A, edges


# adjacency list
def dijkstra2(graph, root):
    n = len(graph)
    # set up "inf" distances
    dist = [Inf for _ in range(n)]
    # set up root distance
    dist[root] = 0
    # set up visited node list
    # visited = [False for _ in range(n)]
    # set up priority queue
    pq = [(0, root)]
    # while there are nodes to process
    while len(pq) > 0:
        # get the root, discard current distance
        _, u = heapq.heappop(pq)
        # if the node is visited, skip
        # if visited[u]:
        #     continue
        # set the node to visited
        # visited[u] = True
        # check the distance and node and distance
        for v, l in graph[u]:
            # if the current node's distance + distance to the node we're visiting
            # is less than the distance of the node we're visiting on file
            # replace that distance and push the node we're visiting into the priority queue
            if dist[u] + l < dist[v]:
                dist[v] = dist[u] + l
                heapq.heappush(pq, (dist[v], v))
    return dist


# adjacency matrix using array for priority queue
def dijkstra1(graph, root):
    n = len(graph)
    dist = [Inf for _ in range(n)]  # set up Inf distances for all nodes
    dist[root] = 0
    pq = [(0, root)]  # priority queue (distance, node)
    while len(pq) > 0:
        # find minimum distance node iterating through array (O(n)) todo insert at right position
        u = pq[0]
        for i in range(len(pq)):
            if pq[i][0] < u[0]:
                u = pq[i]
        pq.remove(u)

        for v in range(n):
            if graph[u[1]][v] != 0:
                if dist[u[1]] + graph[u[1]][v] < dist[v]:
                    dist[v] = dist[u[1]] + graph[u[1]][v]
                    # can change this to append into sorted array
                    pq.append((dist[v], v))
    return dist


def convert_to_adjacency_list(graph, n):
    adjacency_list = defaultdict(list)
    for i in range(n):
        adjacency_list[i] = []
        for j in range(n):
            if graph[i][j] != 0:
                adjacency_list[i].append((j, graph[i][j]))
    return adjacency_list


def print_adjList(adjList):
    for i in adjList:
        print(i, end="")
        for j in adjList[i]:
            print(" -> {}".format(j), end="")
        print()


# Driver program
if __name__ == "__main__":
    V = 100
    # number or edges and probability of creation of edge
    matrix, E = newAdjacencyMatrix(V, 0.3)
    list = convert_to_adjacency_list(matrix, V)
    print(V, "vertices", E, "edges")
    # print(matrix)
    print_adjList(list)

    start = time.time()
    dist = dijkstra1(matrix, 0)  # 1 is the root node
    end = time.time()
    print(dist)
    print(int(round((end - start) * 100000)), "μs")

    start = time.time()
    dist = dijkstra2(list, 0)  # 1 is the root node
    end = time.time()
    print(dist)
    print(int(round((end - start) * 100000)), "μs")

"""
adjacency_matrix array
- memory: O(|V|^2)
- time complexity: O(V^2)
- query: O(V) (linked list)

adjacency_list heap
- memory: O(V + E)
- query: O(1)
- time complexity: O(E * LogV)

edge density = edges / (V * (V - 1))


Results:
100 vertices 467 edges
553 μs
45 μs

100 vertices and 2435 edges
881 μs
96 μs

1000 vertices 249948 edges
103927 μs
4153 μs
"""
