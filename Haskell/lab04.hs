-- Name:
-- Student Number:
-- Username:

module Lab04 where
import Data.Maybe
import Data.Char
import Control.Monad
import Test.QuickCheck

-- keep right on ignoring this ...
test :: Testable p => p -> IO ()
test = quickCheck

{- ======= NOTES =======================================================
If this submission that fails to compile, either
because of errors in your code, or because you made unauthorised changes
that break my *automated testing system*, will score ZERO.

If OK, the marks will be awarded as per annotations in the file.

All labs/exercises get 20 marks

The next section below includes the main type definitions.
Do not alter these in any way. These, by an large, match those given
in lectures.
===================================================================== -}

type Id = String

data Expr
 = Val Double
 | Var Id
 | Add Expr Expr
 | Mul Expr Expr
 | Sub Expr Expr
 | Dvd Expr Expr
 | Def Id Expr Expr
 deriving (Eq,Show)

data Tree k d
 = Nil
 | Node (Tree k d) k d (Tree k d)
 deriving Show
 
define :: Ord k => k -> d -> Tree k d -> Tree k d
define key value Nil = Node Nil key value Nil
define key value (Node l k v r)
 | key < k   =  Node (define key value l) k v r
 | key == k  =  Node l k value r
 | key > k   =  Node l k v (define key value r)
 
find :: Ord k => Tree k d -> k -> Maybe d
find Nil _ = Nothing
find (Node l k v r) key
 | key < k   =  find l key
 | key == k  =  Just v
 | key > k   =  find r key

 
type Dict = Tree Id Double

eval :: Dict -> Expr -> Double
eval _ (Val x) = x
eval d (Var i) = fromJust (find d i)
eval d (Add x y) = eval d x + eval d y
eval d (Mul x y) = eval d x * eval d y
eval d (Sub x y) = eval d x - eval d y
eval d (Dvd x y) = eval d x / eval d y
eval d (Def x e1 e2) = eval (define x (eval d e1) d) e2

data Token
 = Plus | Minus | Times | Over | Lbr | Rbr
 | Let | In | Equal 
 | Ident String | Const Double
 | Junk String
 deriving (Eq, Ord, Show)
 
lexer :: String -> [Token]
lexer str = lexer' (skipWhite str)
 
skipWhite ""  =  ""
skipWhite str@(c:cs)
 | isSpace c  =  skipWhite cs
 | otherwise  =  str
 
lexer' ""  =  []
lexer' (c:cs)
 | isAlpha c  =  lexAlpha [c] cs -- keep what's seen so far
 | isDigit c  =  lexNum [c] cs
 | c == '+'   =  lexPlus cs
 | c == '-'   =  lexMinus cs
 | c == '*'   =  Times : lexer cs
 | c == '/'   =  Over : lexer cs
 | c == '('   =  Lbr : lexer cs
 | c == ')'   =  Rbr : lexer cs
 | c == '='   =  Equal : lexer cs
 | otherwise  =  lexJunk [c] cs

mkId "let" = Let
mkId "in" = In
mkId str = Ident str

mkNum str = Const $ read str

{- ======= Task 1 =====================================================
The following version of 'simp' works for everything except the new 
'Def' construct. Add support for it.                           (4 marks)
===================================================================== -}
simp :: Expr -> Expr
simp (Add e1 e2)
 = let e1' = simp e1
       e2' = simp e2
   in case (e1',e2') of
        (Val 0.0,e)  ->  e
        (e,Val 0.0)  ->  e
        _            ->  Add e1' e2'
simp (Mul e1 e2)
 = let e1' = simp e1
       e2' = simp e2
   in case (e1',e2') of
        (Val 1.0,e)  ->  e
        (e,Val 1.0)  ->  e
        _            ->  Mul e1' e2'
simp (Sub e1 e2) 
 = let e1' = simp e1
       e2' = simp e2
   in case (e1',e2') of
        (e,Val 0.0)  ->  e
        _            ->  Sub e1' e2'
simp (Dvd e1 e2)
 = let e1' = simp e1
       e2' = simp e2
   in case (e1',e2') of
        (e,Val 1.0)  ->  e
        _             ->  Dvd e1' e2'
simp (Def x e1 e2)
 = let {e1' = simp e1;
       e2' = simp e2}
   in case (e1',e2') of
        {_             ->  Def x e1' e2'}
simp e = e  

prop_simpDef
 = simp (Def "x" (Mul (Var "y") (Val 1.0)) (Sub (Var "z") (Val 0.0)))
   ==
   (Def "x" (Var "y") (Var "z") )
   
{- ======= Task 2 =====================================================
The implementation of  the 'lexer' helper functions below is that
provided in class. Extend it to cover the full lexical analysis, as
described the associated image file 'decision.png'            (16 marks)
===================================================================== -}

lexAlpha sofar []  =  [mkId sofar]
lexAlpha sofar str@(c:cs)
 | isAlpha c  =  lexAlpha (sofar++[c]) cs
 | isDigit c  =  lexAlpha (sofar++[c]) cs
 | c == '_'   =  lexAlpha (sofar++[c]) cs
 | otherwise  =  mkId sofar : lexer str

lexNum sofar []  =  [mkNum sofar]
lexNum sofar str@(c:cs)
 | isDigit c  =  lexNum (sofar++[c]) cs
 | c == '.'   =  lexDot (sofar++[c]) cs
 | c == 'E'   =  lexExp (sofar++[c]) cs
 | otherwise  =  mkNum sofar : lexer str

lexDot sofar [] = [mkNum sofar]
lexDot sofar str@(c:cs)
 | isDigit c = lexDotNum (sofar++[c]) cs
 | isDigit c == False = lexJunk (sofar++[c]) cs
 | otherwise = mkNum sofar : lexer str

lexDotNum sofar [] = [mkNum sofar]
lexDotNum sofar str@(c:cs)
 | isDigit c = lexDotNum (sofar++[c]) cs
 | c == 'E' = lexExp (sofar++[c]) cs
 | otherwise = mkNum sofar : lexer str

lexExp sofar [] = [mkNum sofar]
lexExp sofar str@(c:cs)
 | isDigit c = lexExpNum (sofar++[c]) cs
 | c == '+' = lexExpNum (sofar) cs
 | c == '-' = lexExpNum (sofar++[c]) cs
 | otherwise = mkNum sofar : lexer str

lexExpNum sofar [] = [mkNum sofar]
lexExpNum sofar str@(c:cs)
 | isDigit c = lexExpNum (sofar++[c]) cs
 | otherwise = mkNum sofar : lexer str

lexPlus [] = [Plus]
lexPlus str@(c:cs)
 | isDigit c  =  lexNum [c] cs
 | otherwise  =  Plus : lexer str

lexMinus [] = [Minus]
lexMinus str@(c:cs)
 | isDigit c = lexNum (['-']) (c:cs)
 | otherwise = Minus : lexer str

lexJunk  sofar [] = [Junk sofar]
lexJunk sofar str@(c:cs)
 | c /= ' ' = lexJunk (sofar++[c]) cs
 | otherwise = Junk sofar : lexer str

-- This test should always pass, even if you have not done anything !
-- If it fails, then you have broken something that worked.
prop_lexClass
 = lexer "a b1 c_ de let in */()=1 12 123"
   ==
   [Ident "a",Ident "b1",Ident "c_",Ident "de",Let,In,Times,Over,Lbr,
    Rbr,Equal,Const 1.0,Const 12.0,Const 123.0]

-- These tests should fail until you implement the corresponding feature.
prop_lexMinus = lexer "- -42" == [Minus,Const (-42.0)]
prop_lexDot   = lexer "3.14159" == [Const 3.14159]
prop_lexExp   = lexer "3E10 420E-1 314.159E-2" 
                == [Const 3E10, Const 42, Const 3.14159]
prop_lexBadNum  = lexer "123.E456Hello World" 
                == [Junk "123.E456Hello", Ident "World"]
prop_lexJunk  = lexer "$abc !123 goodbye!"
                == [Junk "$abc", Junk "!123", Ident "goodbye", Junk "!"]

{- ==== That's all, folks ! ========================================= -}

