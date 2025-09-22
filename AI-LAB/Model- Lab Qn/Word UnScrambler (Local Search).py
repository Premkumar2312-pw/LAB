import random

dict_words = input("Enter dictionary words (space separated): ").split()
DICTIONARY = set(dict_words)

scrambled_word = input("Enter the scrambled word: ")

def score(word, target_letters):
    return sum(1 for w, t in zip(word, target_letters) if w == t)

def local_search_unscramble(scrambled, dictionary, max_iter=5000):
    letters = list(scrambled)
    best = letters[:]
    best_score = 0

    for _ in range(max_iter):
        i, j = random.sample(range(len(letters)), 2)
        letters[i], letters[j] = letters[j], letters[i]
        candidate = "".join(letters)

        if candidate in dictionary:
            return candidate
          
        s = score(candidate, best)
        if s > best_score:
            best_score = s
            best = letters[:]
        else:
            letters[i], letters[j] = letters[j], letters[i]

    return "".join(best)

unscrambled = local_search_unscramble(scrambled_word, DICTIONARY)
print("Scrambled:", scrambled_word)
print("Unscrambled (best attempt):", unscrambled)


##Method 2

import random
goal = input("Enter a word: ")
s = ''.join(random.sample(goal, len(goal)))
for _ in range(100):
    i, j = random.sample(range(len(goal)), 2)
    t = list(s)
    t[i], t[j] = t[j], t[i]
    t = ''.join(t)
    print(t)
    if sum(a == b for a, b in zip(t, goal)) >= sum(a == b for a, b in zip(s, goal)):
        s = t
    if s==goal:
        break
print("Unscrambled:", s)