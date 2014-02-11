-- Name: Fintan Halpenny
-- Student Number: 11325486
-- Username: rossmanf

module Lab01 where
import Control.Monad
import Data.Char
import Test.QuickCheck

-- ignore this for now....
test :: Testable p => p -> IO ()
test = quickCheck

{- ======= NOTES =======================================================
For Lab 1 ONLY: half the marks are as detailed below.
The other half marks are awarded if your submission compiles OK, and you
have left well alone ....
If not, you get a mark out of 10 reflecting how badly you failed to compile.

For future labs/exercises, any submission that fails to compile, either
because of errors in your code, or because you made unauthorised changes
that break my *automated testing system*, will score ZERO.

If OK, the marks will be awarded as per annotations in the file.

All labs/exercises get 20 marks
===================================================================== -}


{- ======= Task 1 =====================================================
Define variable 'myself' to be your TCD username.          (1 mark)
===================================================================== -}
myself = "rossmanf"



{- ======= Task 2 =====================================================
Implement the 'takesome' example from lectures.
**Note: for the best learning outcome, type it in manually, rather
than 'cut-n-paste' form the lecture notes. You are more likely to
stumble upon errors and how to get around them that way.       (4 marks)
===================================================================== -}
takesome 0 _ = []
takesome _ [] = []
takesome n (x:xs) = x: (take(n-1) xs)

-- what's this? (!!! Leave it alone !)
prop_takesome_1 n xs = n >= 0 ==> xs == takesome n xs ++ drop n xs

{- ======= Task 3 =====================================================
Write function 'sumsq' to compute the sum of squares of a numeric list
                                                              (5 marks)
===================================================================== -}
sumsq [] = 0
sumsq [x] = x*x
sumsq (x:xs) = x*x + (sumsq(xs))

-- more of this 'prop' stuff !!! Keep leaving it alone !   >:(  
prop_sumsq_nil = sumsq [] == 0
prop_sumsq_sngl n = sumsq [n] == n*n

{- ==== That's all, folks ! ========================================= -}

