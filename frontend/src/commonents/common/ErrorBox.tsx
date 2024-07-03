import React from 'react';

interface ErrorBoxProps {
  error: string;
}

const ErrorBox: React.FC<ErrorBoxProps> = ({error}) => {
  return <h5>{error}</h5>;
};

export default ErrorBox;
