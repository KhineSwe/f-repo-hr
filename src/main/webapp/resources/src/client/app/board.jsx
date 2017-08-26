import React from 'react';
import {render} from 'react-dom';



class Board extends React.Component {
  render() {
    return (
        <div>
          This is board area
        </div>
    );
  }
}

render(<Board/>, document.getElementById('app'));
