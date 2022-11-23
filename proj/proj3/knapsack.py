"""
Project 3: Dynamic Programming
We have a knapsack of capacity weight C (a positive integer) and n types of objects.
Each object of the ith type has weight wi and profit pi (all wi and all p i are positive
integers, i = 0, 1, ..., n-1). There are unlimited supplies of each type of objects. Find
the largest total profit of any set of the objects that fits in the knapsack.
Let P(C) be the maximum profit that can be made by packing objects into the knapsack
of capacity C.

C: the capacity of the knapsack
w: a list of weights of the objects
p: a list of profits of the objects

Give a dynamic programming algorithm to compute the maximum profit, given a
knapsack of capacity C, n types of objects with weights w and profits p using the
bottom up approach.
"""


def knapsack(C, w, p):
    n = len(w)
    # Create a matrix of size C x n
    # Initialize the matrix with 0
    # The matrix is a list of lists
    matrix = [[0 for _ in range(n + 1)] for _ in range(C + 1)]
    # Fill in the matrix
    for i in range(1, C + 1):
        for j in range(1, n + 1):  # for each object
            # if the object is too heavy
            if w[j - 1] > i:
                matrix[i][j] = matrix[i][j - 1]
            else:
                # choose the max of the two options
                # 1. the object is not included
                # 2. the object is included (profit of new object + the remaining max profit)
                matrix[i][j] = max(
                    matrix[i][j - 1], matrix[i - w[j - 1]][j - 1] + p[j - 1]
                )
    # Return the last element of the matrix
    return matrix[C][n]


# unlimited supply of each type of objects
def knapsack2(C, w, p):
    n = len(w)
    dp = [0 for _ in range(C + 1)]

    for i in range(0, C + 1):
        # print("i = ", i)
        for j in range(0, n):
            if w[j] < i:
                dp[i] = max(dp[i], dp[i - w[j]] + p[j])
                # print("dp[" + str(i) + "] and dp[" + str(i - w[j]) + "] + " + str(p[j]))
    return dp[C]


if __name__ == "__main__":
    C = 14
    w = [4, 6, 8]
    p = [7, 6, 9]
    print(knapsack2(C, w, p))

    w = [5, 6, 8]
    p = [7, 6, 9]
    print(knapsack2(C, w, p))
