a = [1, 2, 4]
b = [4, 1, 3, 4, 2]
dp = [[0 for _ in range(len(b) + 1)] for _ in range(len(a) + 1)]

for i in range(1, len(a) + 1):
    for j in range(1, len(b) + 1):
        if a[i - 1] == b[j - 1]:
            dp[i][j] = dp[i - 1][j - 1] + 1  # match
        else:
            dp[i][j] = max(dp[i - 1][j], dp[i][j - 1])

# getting the sequence
i, j = len(a), len(b)
seq = []
while i > 0 and j > 0:
    if a[i - 1] == b[j - 1]:
        seq.append(a[i - 1])
        i -= 1
        j -= 1
    elif dp[i - 1][j] > dp[i][j - 1]:
        i -= 1
    else:
        j -= 1

print(seq[::-1])
