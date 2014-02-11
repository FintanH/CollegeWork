-- Name: Fintan Leon Rossmann Halpenny
-- Student Number: 11325486
-- Username: rossmanf

module Lab03 where
import Control.Monad
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
Complete the definition of 'simp' from Class 9 (here renamed 'simp1')
on the initial version of the datatype (here renamed Expr1)   (8 marks)
===================================================================== -}

data Expr1
 = Val1 Float
 | Add1 Expr1 Expr1
 | Mul1 Expr1 Expr1
 | Sub1 Expr1 Expr1
 | Dvd1 Expr1 Expr1
 deriving (Eq,Show)

simp1 :: Expr1 -> Expr1
simp1 (Val1 x) = (Val1 x)
simp1 (Add1 e1 e2) = let (Val1 x) = simp1 e1
                         (Val1 y) = simp1 e2
                     in Val1 (x+y)
simp1 (Mul1 e1 e2) = let (Val1 x) = simp1 e1
                         (Val1 y) = simp1 e2
                     in Val1 (x*y)
simp1 (Sub1 e1 e2) = let (Val1 x) = simp1 e1
                         (Val1 y) = simp1 e2
                     in Val1 (x-y)
simp1 (Dvd1 e1 e2) = let (Val1 x) = simp1 e1
                         (Val1 y) = simp1 e2
                     in Val1 (x/y)
--simp1 (Mul1 e1 e2) = error "simp1 Mul1 not yet done" -- similar to above
--simp1 (Sub1 e1 e2) = error "simp1 Sub1 not yet done"
--simp1 (Dvd1 e1 e2) = error "simp1 Dvd1 not yet done"

-- as ever, leave this alone !
prop_simp1_complex
 = simp1 
     (Mul1 
       (Val1 3)
       (Add1
         (Dvd1 (Val1 8) (Val1 2))
         (Sub1 (Val1 20) (Val1 10))
       )
     )
   ==
   Val1 42.0

{- ======= Task 2 =====================================================
Complete the definition of 'simp' from Class 9 (here renamed 'simp2')
on the extended version of the datatype (here renamed Expr2). (12 marks)
===================================================================== -}

type Ident = String

data Expr2
 = Val2 Float
 | Add2 Expr2 Expr2
 | Mul2 Expr2 Expr2
 | Sub2 Expr2 Expr2
 | Dvd2 Expr2 Expr2
 | Var2 Ident
 deriving (Eq,Show)


simp2 :: Expr2 -> Expr2
simp2 (Add2 e1 e2)
 = let e1' = simp2 e1
       e2' = simp2 e2
   in case (e1',e2') of
        (Val2 0.0,e)  ->  e
        (e,Val2 0.0)  ->  e
        _             ->  Add2 e1' e2'
simp2 (Mul2 e1 e2)
 = let e1' = simp2 e1
       e2' = simp2 e2
   in case (e1',e2') of
        (Val2 1.0,e)  ->  e
        (e,Val2 1.0)  ->  e
        _             ->  Mul2 e1' e2'
simp2 (Sub2 e1 e2)
 = let e1' = simp2 e1
       e2' = simp2 e2
   in case (e1',e2') of
        (Val2 0.0,e)  ->  Sub2 e1' e2'
        (e,Val2 0.0)  ->  e
        _             ->  Sub2 e1' e2'
simp2 (Dvd2 e1 e2)
 = let e1' = simp2 e1
       e2' = simp2 e2
   in case (e1',e2') of
        (Val2 0.0,e)  ->  Val2 0.0
        (e,Val2 1.0)  ->  e
        _             ->  Dvd2 e1' e2'
--simp2 (Mul2 e1 e2) = error "simp2 Mul2 not yet done"
--simp2 (Sub2 e1 e2) = error "simp2 Sub2 not yet done"
--simp2 (Dvd2 e1 e2) = error "simp2 Dvd2 not yet done"
simp2 e = e  

prop_simp2_complex
 = simp2 
     (Mul2 
       (Val2 1)
       (Add2
         (Dvd2 (Val2 40) (Val2 1))
         (Sub2 (Val2 2) (Val2 0))
       )
     )
   ==
   Add2 (Val2 40) (Val2 2)
    
{- ==== That's all, folks ! ========================================= -}

