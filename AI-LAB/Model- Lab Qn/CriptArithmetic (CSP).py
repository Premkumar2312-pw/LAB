from itertools import permutations

def solve_cryptarithm(words, result):
   
    letters = set("".join(words) + result)
    if len(letters) > 10:
        print("Too many unique letters (>10) for digits 0-9!")
        return

    letters = list(letters)
    first_letters = set(w[0] for w in words + [result])

    for perm in permutations(range(10), len(letters)):
        assign = dict(zip(letters, perm))

        if any(assign[ch] == 0 for ch in first_letters):
            continue
        word_values = [int("".join(str(assign[ch]) for ch in w)) for w in words]
        result_value = int("".join(str(assign[ch]) for ch in result))

   
        if sum(word_values) == result_value:
            print("\n Solution Found!")
            for k in sorted(assign.keys()):
                print(f"{k} = {assign[k]}")
            print(f"{' + '.join(str(wv) for wv in word_values)} = {result_value}")
            return

    print("\n No solution found.")

words = input("Enter words to add (space-separated): ").split()
result = input("Enter result word: ")

solve_cryptarithm(words, result)