import React, { useState } from 'react';
import { unaryCall } from '../services/demoServiceClient';

export const DemoComponent = () => {
  const [response, setResponse] = useState('');

  const handleClick = async () => {
    try {
      const res = await unaryCall('Hello from frontend!');
      setResponse(res);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      <button onClick={handleClick}>Send Unary Call</button>
      <p>Response: {response}</p>
    </div>
  );
};
