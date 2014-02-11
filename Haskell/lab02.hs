-- Name: Fintan Leon Rossmann Halpenny
-- Student Number: 11325486
-- Username: rossmanf

module Lab02 where
import Control.Monad
import Data.Char
import Data.List
import Test.QuickCheck

-- ignore this for now....
test :: Testable p => p -> IO ()
test = quickCheck

{- ======= NOTES =======================================================
If this submission that fails to compile, either
because of errors in your code, or because you made unauthorised changes
that break my *automated testing system*, will score ZERO.

If OK, the marks will be awarded as per annotations in the file.

All labs/exercises get 20 marks
===================================================================== -}


{- ======= Task 1 =====================================================
Write a function 'isConsonant' that takes a character input
and returns True if that character is a consonant              (7 marks)
===================================================================== -}
lower_consonants = (\\) ['a'..'z'] ['a','e','i','o','u']
upper_consonants = (\\) ['A'..'Z'] ['A','E','I','O','U']
isConsonant :: Char -> Bool
isConsonant c = elem c lower_consonants || elem c upper_consonants
--isConsonant = error "***** Lab 2, Task 2 not yet done!"


{- ======= Task 2 =====================================================
Write a function 'repConsonant' that takes a character input
and replaces it with '!' if a consonant , but otherwise leaves
it unchanged.                                                  (7 marks)
===================================================================== -}
repConsonant :: Char -> Char
repConsonant c = if isConsonant c then '!' else c
--repConsonant = error "***** Lab 2, Task 2 not yet done!"

-- leave this alone!
prop_Consonant c 
  =  if isConsonant c 
     then repConsonant c == '!'
     else repConsonant c == c
     
{- ======= Task 3 =====================================================
Write a function 'repConsonants' that takes a string input
and replaces all consonants with '!', leaving other characters unchanged.
Hint, lookup 'map' in the Standard Prelude                     (6 marks)
===================================================================== -}
repConsonants :: String -> String
repConsonants [] = []
repConsonants (x:xs) = repConsonant x : repConsonants xs
--repConsonants = error "repConsonants NYI"

-- leave this alone !
prop_Consonants cs 
  =  all (\ c-> if isConsonant c 
                then repConsonant c == '!'
                else repConsonant c == c)
         (repConsonants cs)
     
{- ==== That's all, folks ! ========================================= -}

