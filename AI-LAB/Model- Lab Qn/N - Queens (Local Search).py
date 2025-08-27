import random

def create_board(n):
    return [random.randint(0, n-1) for _ in range(n)]

def conflicts(board):
    n = len(board)
    count = 0
    for i in range(n):
        for j in range(i + 1, n):
            if board[i] == board[j] or abs(board[i] - board[j]) == j - i:
                count += 1
    return count

def get_best_neighbor(board):
    n = len(board)
    best_board = board[:]
    min_conflicts = conflicts(board)
    for col in range(n):
        original_row = board[col]
        for row in range(n):
            if row != original_row:
                board[col] = row
                current_conflicts = conflicts(board)
                if current_conflicts < min_conflicts:
                    min_conflicts = current_conflicts
                    best_board = board[:]
        board[col] = original_row
    return best_board, min_conflicts

def hill_climb(n, max_restarts=1000):
    for _ in range(max_restarts):
        board = create_board(n)
        while True:
            board, min_conf = get_best_neighbor(board)
            if min_conf == 0:
                return board
            if conflicts(board) == min_conf:  # stuck in local minimum
                break
    return None

def print_board(board):
    n = len(board)
    for row in range(n):
        line = ""
        for col in range(n):
            line += "Q " if board[col] == row else ". "
        print(line)
    print("\n")

# User input
N = int(input("Enter the number of queens (N): "))
solution = hill_climb(N)
if solution:
    print("Solution found:")
    print_board(solution)
else:
    print("No solution found.")