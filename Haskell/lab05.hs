-- Name:
-- Student Number:
-- Username:

module Lab05 where
import Data.Maybe
import Data.Char
import Data.Either
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

The next section below includes the main type and function definitions.
Do not alter these in any way. These, by and large, match those given
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

lexDot sofar [] =  [Junk sofar]
lexDot sofar (c:cs)
 | isDigit c  =  lexDotNum (sofar++[c]) cs
 | otherwise  =  lexJunk (sofar++[c]) cs
 
lexDotNum sofar [] =  [mkNum sofar]
lexDotNum sofar str@(c:cs)
  | isDigit c  =  lexDotNum (sofar++[c]) cs
  | c == 'E'   =  lexExp (sofar++[c]) cs
  | otherwise  =  mkNum sofar : lexer str

lexExp sofar []  =  [Junk sofar]
lexExp sofar (c:cs)
  | c == '+'  =  lexExp2 (sofar++[c]) cs
  | c == '-'  =  lexExp2 (sofar++[c]) cs
  | isDigit c  =  lexExpNum (sofar++[c]) cs
  | otherwise  =  lexJunk (sofar++[c])  cs
  
lexExp2 sofar [] = [Junk sofar]
lexExp2 sofar (c:cs)
 | isDigit c  =  lexExpNum (sofar++[c]) cs
 | otherwise  =  lexJunk (sofar++[c]) cs
 
lexExpNum sofar []   =  [mkNum sofar]
lexExpNum sofar str@(c:cs)
 | isDigit c  =  lexExpNum (sofar++[c]) cs
 | otherwise  =  mkNum sofar : lexer str

lexPlus [] = [Plus]
lexPlus str@(c:cs)
 | isDigit c  =  lexNum [c] cs
 | otherwise  =  Plus : lexer str

lexMinus []  =  [Minus]
lexMinus str@(c:cs)
 | isDigit c  =  lexNum ['-',c] cs
 | otherwise  =  Minus : lexer str

lexJunk sofar []  = [Junk sofar]
lexJunk sofar str@(c:cs)
  | isSpace c  =  Junk sofar : lexer str
  | otherwise  =  lexJunk (sofar++[c]) cs

{- ======= Task 1 =====================================================
The implementation of  the parser below is that
provided in Class 23. Complete it.                             (8 marks)
===================================================================== -}

parseExpr toks 
 = let (mulExpr,toks1) = parseMulExpr toks 
   in case toks1 of 
       [] -> (mulExpr,[]) 
       (Plus:toks2)
        -> let (expr,toks3) = parseExpr toks2
           in (Add mulExpr expr,toks3) 
       (Minus:toks2)
        -> let (expr,toks3) = parseExpr toks2
           in (Sub mulExpr expr,toks3) 
       _ -> (mulExpr,toks1)

parseMulExpr toks
 = let (baseExpr,toks1) = parseBase toks 
   in case toks1 of 
       [] -> (baseExpr,[]) 
       (Times:toks2)
        -> let (expr,toks3) = parseMulExpr toks2
           in (Mul baseExpr expr,toks3) 
       (Over:toks2)
        -> let (expr,toks3) = parseMulExpr toks2
           in (Dvd baseExpr expr,toks3) 
       _ -> (baseExpr,toks1)

parseBase [] = perr "no tokens" []
parseBase ((Ident v):rest) = (Var v,rest)
parseBase ((Const r):rest) = (Val r,rest)
parseBase (Lbr:rest) 
 = let (e,rest1) = parseExpr rest 
    in case rest1 of 
         [] -> perr "premature end of ()" []  
         (Rbr:rest'2) -> (e,rest'2)  
         reste -> perr "no closing bracket" reste
parseBase (Let:Ident i:Equal:rest)  
 = let (e1,rest1) = parseExpr rest  
   in case rest1 of 
     (In:rest2)
       -> let (e2,rest3) = parseExpr rest2
          in (Def i e1 e2,rest3) 
     reste -> perr "in <expr> not found" reste 

perr msg toks = (Var msg,toks)

parse :: String -> (Expr,[Token])
parse = parseExpr . lexer

-- tests - leave alone!
ptst01 :: Bool
ptst01 = parse "" == (Var "no tokens", [])
ptst02 = parse "42" == (Val 42.0, [])
ptst03 = parse "v" == (Var "v", [])
ptst04 = parse "a+b" == (Add (Var "a") (Var "b"), [])
ptst05 = parse "a-b" == (Sub (Var "a") (Var "b"), [])
ptst06 = parse "a*b" == (Mul (Var "a") (Var "b"), [])
ptst07 = parse "a/b" == (Dvd (Var "a") (Var "b"), [])
ptst08 = parse "a+b*c" == (Add (Var "a") (Mul (Var "b") (Var "c")), [])
ptst09 = parse "(a+b)*c" == (Mul (Add (Var "a") (Var "b")) (Var "c"), [])
ptst10 = parse "a*b+c" == (Add (Mul (Var "a") (Var "b")) (Var "c"), [])
ptst11 = parse "a*(b+c)" == (Mul (Var "a") (Add (Var "b") (Var "c")), [])
ptst12 = parse "let x=3 in y" == (Def "x" (Val 3.0) (Var "y"), [])
ptst13 = parse "let x=3 in let y=4 in z" == (Def "x" (Val 3.0) (Def "y" (Val 4.0) (Var "z")), [])
ptst14 = parse "let x=(let y=3 in z) in w" == (Def "x" (Def "y" (Val 3.0) (Var "z")) (Var "w"), [])


{- ======= Task 2 =====================================================
Given the PResult datatype below, made an instance of Monad,
recode the Class 23 Parser as `eParser' to use this monad to do error 
handling                                                      (12 marks)
===================================================================== -}
data PResult a = Err String | Res a deriving (Eq,Show)

instance Monad PResult where
  return         =  Res
  (Err e) >>= _  =  Err e
  (Res r) >>= p  =  p r
  fail           =  Err

eParser :: [Token] -> PResult (Expr,[Token])
eParser toks = 
  do (mulExpr,toks1) <- emParser toks
     parseESign toks1 mulExpr

parseESign toks mulExpr =
  case toks of
    [] -> return (mulExpr,[])
    (Plus:toks2) -> 
      do (expr,toks3) <- eParser toks2
         return (Add mulExpr expr,toks3) 
    (Minus:toks2) -> 
      do (expr,toks3) <- eParser toks2
         return (Sub mulExpr expr,toks3)
    _ -> return (mulExpr,toks)

emParser :: [Token] -> PResult (Expr,[Token])
emParser toks = do
  (baseExpr,toks1) <- bParser toks
  parseMSign toks1 baseExpr

parseMSign toks baseExpr =
  case toks of
    [] -> return (baseExpr,[])
    (Times:toks2) -> 
      do (expr,toks3) <- emParser toks2
         return (Mul baseExpr expr,toks3) 
    (Over:toks2) -> 
      do (expr,toks3) <- emParser toks2
         return (Dvd baseExpr expr,toks3)
    _ -> return (baseExpr,toks)

bParser [] = Err "no tokens"
bParser ((Ident v):rest) = return (Var v,rest)
bParser ((Const r):rest) = return (Val r,rest)
bParser (Lbr:rest) = 
  do (e,rest1) <- eParser rest
     parseBracket rest1 e
bParser (Let:Ident i:Equal:rest) =
  do (e1,rest1) <- eParser rest
     parseAssign rest1 e1 i

parseBracket rest1 e =
  case rest1 of 
   [] -> Err "premature end of ()" 
   (Rbr:rest'2) -> return (e,rest'2)  
   reste -> Err "no closing bracket"

parseAssign rest1 e1 i =
  case rest1 of
    (In:rest2)
      -> do (e2,rest3) <- eParser rest2
            return (Def i e1 e2,rest3)
    reste -> Err "in <expr> not found"

rparse = eParser . lexer

-- more tests
rtst01 = rparse "" == Err "no tokens"
rtst02 = rparse "42" == Res (Val 42.0, [])
rtst03 = rparse "v" == Res (Var "v", [])
rtst04 = rparse "a+b" == Res (Add (Var "a") (Var "b"), [])
rtst05 = rparse "a-b" == Res (Sub (Var "a") (Var "b"), [])
rtst06 = rparse "a*b" == Res (Mul (Var "a") (Var "b"), [])
rtst07 = rparse "a/b" == Res (Dvd (Var "a") (Var "b"), [])
rtst08 = rparse "a+b*c" == Res (Add (Var "a") (Mul (Var "b") (Var "c")), [])
rtst09 = rparse "(a+b)*c" == Res (Mul (Add (Var "a") (Var "b")) (Var "c"), [])
rtst10 = rparse "a*b+c" == Res (Add (Mul (Var "a") (Var "b")) (Var "c"), [])
rtst11 = rparse "a*(b+c)" == Res (Mul (Var "a") (Add (Var "b") (Var "c")), [])
rtst12 = rparse "let x=3 in y" == Res (Def "x" (Val 3.0) (Var "y"), [])
rtst13 = rparse "let x=3 in let y=4 in z" == Res (Def "x" (Val 3.0) (Def "y" (Val 4.0) (Var "z")), [])
rtst14 = rparse "let x=(let y=3 in z) in w" == Res (Def "x" (Def "y" (Val 3.0) (Var "z")) (Var "w"), [])




{- ==== That's all, folks ! ========================================= -}

