import math
import random

board = [' ' for _ in range(9)]

def print_board():
    for row in [board[i*3:(i+1)*3] for i in range(3)]:
        print('| ' + ' | '.join(row) + ' |')

def check_winner(brd, player):
    win_conditions = [
        [0,1,2], [3,4,5], [6,7,8],
        [0,3,6], [1,4,7], [2,5,8],
        [0,4,8], [2,4,6]
    ]
    return any(all(brd[i] == player for i in condition) for condition in win_conditions)

def is_draw():
    return ' ' not in board

def player_move():
    while True:
        try:
            move = int(input("Enter your move (1-9): ")) - 1
            if 0 <= move <= 8 and board[move] == ' ':
                board[move] = 'X'
                break
            else:
                print("Invalid move. Try again.")
        except:
            print("Enter a number between 1 and 9.")

def minimax(brd, depth, is_maximizing, max_depth=2):
    if check_winner(brd, 'O'):
        return 10 - depth
    if check_winner(brd, 'X'):
        return depth - 10
    if ' ' not in brd or depth == max_depth:
        return 0
    if is_maximizing:
        best_score = -math.inf
        for i in range(9):
            if brd[i] == ' ':
                brd[i] = 'O'
                score = minimax(brd, depth + 1, False, max_depth)
                brd[i] = ' '
                best_score = max(best_score, score)
        return best_score
    else:
        best_score = math.inf
        for i in range(9):
            if brd[i] == ' ':
                brd[i] = 'X'
                score = minimax(brd, depth + 1, True, max_depth)
                brd[i] = ' '
                best_score = min(best_score, score)
        return best_score

def computer_move():
    best_score = -math.inf
    best_moves = []
    for i in range(9):
        if board[i] == ' ':
            board[i] = 'O'
            score = minimax(board, 0, False, max_depth=2)
            board[i] = ' '
            if score > best_score:
                best_score = score
                best_moves = [i]
            elif score == best_score:
                best_moves.append(i)
    move = random.choice(best_moves)
    board[move] = 'O'
    print(f"Computer chose position {move + 1}")

def play_game():
    print("Welcome to Tic Tac Toe (You: X, Computer: O)")
    print_board()
    while True:
        player_move()
        print_board()
        if check_winner(board, 'X'):
            print("You win!")
            break
        if is_draw():
            print("It's a draw!")
            break
        computer_move()
        print_board()
        if check_winner(board, 'O'):
            print("Computer wins!")
            break
        if is_draw():
            print("It's a draw!")
            break

play_game()
