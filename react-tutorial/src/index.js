import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

function NormalSquare(props) {
    return (
        <button className="square" onClick={props.onClick}>
            {props.value}
        </button>
    );
}


function WinKeyPointSquare(props) {
    return (
        <button className="square square-win-key-point" onClick={props.onClick}>
            {props.value}
        </button>
    );
}

function AscendingMoves(props) {
    return (
        <button className="moves-sort-btn" onClick={() => props.onClick("asc")}>
            내림차순
        </button>
    );
}

function DescendingMoves(props) {
    return (
        <button className="moves-sort-btn" onClick={() => props.onClick("desc")}>
            오름차순
        </button>
    );
}

class MovesBoard extends React.Component {
    render() {
        return (
            <div>
                {AscendingMoves(this.props)}
                {DescendingMoves(this.props)}
            </div>
        );
    }
}

class Board extends React.Component {
    renderSquare(i, x, y) {
        const squares = this.props.squares;

        const numbers = winKeyPoint(squares);

        if (numbers !== null && numbers.includes(i)) {
            return <WinKeyPointSquare
                key={i}
                value={this.props.squares[i]}
                onClick={() => this.props.onClick(i, x, y)}
            />;
        } else {
            return <NormalSquare
                key={i}
                value={this.props.squares[i]}
                onClick={() => this.props.onClick(i, x, y)}
            />;
        }
    }

    makeBoard(index, j) {
        let result = [];
        for (let i = 0; i < 3; i++) {
            result.push(this.renderSquare((index * 3) + i, j % 3, i));
        }
        return result;
    }

    makeUpperBoard() {
        let result = [];
        for (let i = 0; i < 3; i++) {
            result.push(
                <div key={i} className="board-row">
                    {this.makeBoard(i, i)}
                </div>)

        }
        return result;
    }

    render() {
        return (
            <div>
                {this.makeUpperBoard()}
            </div>
        );
    }
}

class Game extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            history: [{
                squares: Array(9).fill(null),
                lastX: null,
                lastY: null,
            }],
            sortDirection: null,
            stepNumber: 0,
            xIsNext: true,
        }
    }

    handleClick(i, x, y) {
        const history = this.state.history.slice(0, this.state.stepNumber + 1);
        const current = history[history.length - 1];
        const squares = current.squares.slice();

        if (calculateWinner(current.squares) || squares[i]) {
            return;
        }

        squares[i] = this.state.xIsNext ? 'X' : 'O';
        this.setState({
            history: history.concat([{
                squares: squares,
                lastX: x,
                lastY: y,
            }]),
            stepNumber: history.length,
            xIsNext: !this.state.xIsNext,
        });
    }

    sortClick(sortOption) {
        const history = this.state.history.slice(0, this.state.stepNumber + 1);

        console.log(history);
        this.setState({
            sortDirection: sortOption
        })
    }

    jumpTo(step) {
        this.setState({
            stepNumber: step,
            xIsNext: (step % 2) === 0,
        });
    }

    render() {
        const history = this.state.history;
        const current = history[this.state.stepNumber];
        const winner = calculateWinner(current.squares);
        const isDraw = calculateDraw(current.squares);
        const sortDirection = this.state.sortDirection;

        let moves = history.map((step, move) => {
            console.log(move);
            const desc = move ? 'Go to move #' + move : 'Go to game start';
            const lastXYDesc = move ? ' Last X :' + history[move].lastX + ' Last Y : ' + history[move].lastY : "";

            return (
                <li key={move}>
                    <button className={"move-button"} onClick={() => this.jumpTo(move)}>
                        {desc} {lastXYDesc}
                    </button>
                </li>
            )
        });

        if (sortDirection === 'desc') {
            moves.reverse();
        } else {
            moves.sort();
        }


        let status;

        if (winner) {
            status = 'Winner: ' + winner;
        } else {
            status = 'Next player: ' + (this.state.xIsNext ? 'X' : 'O');
        }

        if (!winner && isDraw) {
            status = 'Draw game';
        }

        return (
            <div className="game">
                <div className={"game-board"}>
                    <Board
                        squares={current.squares}
                        onClick={(i, x, y) => this.handleClick(i, x, y)}
                    />
                </div>

                <div className={"game-info"}>
                    <div>{status}</div>
                    <div>{moves}</div>
                </div>

                <div className={"sort-board"}>
                    <MovesBoard
                        onClick={(sortOption) => this.sortClick(sortOption)}
                    />
                </div>
            </div>
        )
    }
}

function calculateWinner(squares) {
    const lines = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6],
    ];

    for (const element of lines) {
        const [a, b, c] = element;
        if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
            return squares[a];
        }
    }

    return null;
}

function winKeyPoint(squares) {
    const lines = [
        [0, 1, 2],
        [3, 4, 5],
        [6, 7, 8],
        [0, 3, 6],
        [1, 4, 7],
        [2, 5, 8],
        [0, 4, 8],
        [2, 4, 6],
    ];

    for (const element of lines) {
        const [a, b, c] = element;
        if (squares[a] && squares[a] === squares[b] && squares[a] === squares[c]) {
            return element;
        }
    }

    return null;

}

function calculateDraw(squares) {
    let isDone = true;

    squares.forEach(i => {
        if (i === null) {
            isDone = false;
        }
    });

    if (isDone) {
        return true;
    }

    return null;
}

// =========================================

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(<Game/>);