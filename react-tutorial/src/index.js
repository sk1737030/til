import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';

function Square(props) {
    return (
        <button className="square" onClick={props.onClick}>
            {props.value}
        </button>
    );
}

class Board extends React.Component {
    renderSquare(i, x, y) {
        return <Square value={this.props.squares[i]}
                       onClick={() => this.props.onClick(i, x, y)}
        />;
    }

    render() {
        return (
            <div>
                <div className="board-row">
                    {this.renderSquare(0, 0, 0)}
                    {this.renderSquare(1, 1, 0)}
                    {this.renderSquare(2, 2, 0)}
                </div>
                <div className="board-row">
                    {this.renderSquare(3, 0, 1)}
                    {this.renderSquare(4, 1, 1)}
                    {this.renderSquare(5, 2, 1)}
                </div>
                <div className="board-row">
                    {this.renderSquare(6, 0, 2)}
                    {this.renderSquare(7, 1, 2)}
                    {this.renderSquare(8, 2, 2)}
                </div>
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
        const isDraw = calculateDarw(current.squares);

        const moves = history.map((step, move) => {
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

function calculateDarw(squares) {
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