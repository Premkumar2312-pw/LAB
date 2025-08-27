def is_safe(square, row, col, num, n):
    for k in range(n):
        if square[row][k] == num or square[k][col] == num:
            return False
    return True

def latin_square(square, row, col, n):
    if row == n:
        return True
    next_row, next_col = (row, col + 1) if col + 1 < n else (row + 1, 0)
    for num in range(1, n + 1):
        if is_safe(square, row, col, num, n):
            square[row][col] = num
            if latin_square(square, next_row, next_col, n):
                return True
            square[row][col] = 0
    return False

n = int(input("Enter the size of Latin Square (N): "))
square = [[0 for _ in range(n)] for _ in range(n)]

if latin_square(square, 0, 0, n):
    for row in square:
        print(' '.join(map(str, row)))
else:
    print("No solution found.")