import math

def game_over(heaps):
    return all(h == 0 for h in heaps)

def get_moves(heaps, k):
    moves = []
    for i, h in enumerate(heaps):
        for take in range(1, min(k, h) + 1):
            moves.append((i, take))
    return moves

def apply_move(heaps, move):
    i, take = move
    new_heaps = heaps[:]
    new_heaps[i] -= take
    return new_heaps

def alpha_beta(heaps, depth, alpha, beta, maximizing):
    if game_over(heaps) or depth == 0:
        return 1 if not maximizing else -1
    moves = get_moves(heaps, k=3)
    if maximizing:
        best_val = -math.inf
        for move in moves:
            val = alpha_beta(apply_move(heaps, move), depth-1, alpha, beta, False)
            best_val = max(best_val, val)
            alpha = max(alpha, val)
            if beta <= alpha: break
        return best_val
    else:
        best_val = math.inf
        for move in moves:
            val = alpha_beta(apply_move(heaps, move), depth-1, alpha, beta, True)
            best_val = min(best_val, val)
            beta = min(beta, val)
            if beta <= alpha: break
        return best_val

def best_move(heaps, depth=2):
    best_val = -math.inf
    best_action = None
    for move in get_moves(heaps, k=3):
        val = alpha_beta(apply_move(heaps, move), depth-1, -math.inf, math.inf, False)
        if val > best_val:
            best_val = val
            best_action = move
    return best_action

def display_board(heaps):
    print("\nCurrent Heaps:")
    for i, h in enumerate(heaps):
        print(f"Heap {i}: " + "o " * h)

def play_nim():
    n = int(input("Enter number of heaps: "))
    heaps = []
    for i in range(n):
        heaps.append(int(input(f"Enter stones in heap {i}: ")))
    display_board(heaps)

    while not game_over(heaps):
        print("\nYour turn!")
        heap_idx = int(input(f"Choose heap (0-{len(heaps)-1}): "))
        stones = int(input("Stones to remove (1-3): "))
        if 0 <= heap_idx < len(heaps) and 1 <= stones <= 3 and heaps[heap_idx] >= stones:
            heaps[heap_idx] -= stones
        else:
            print("Invalid move! Try again.")
            continue
        display_board(heaps)
        if game_over(heaps):
            print("You win!")
            break

        ai_heap, ai_take = best_move(heaps)
        heaps[ai_heap] -= ai_take
        print(f"\nAI removes {ai_take} stone(s) from heap {ai_heap}")
        display_board(heaps)
        if game_over(heaps):
            print("AI wins!")
            break

play_nim()
