import React from 'react';
import {BadRequestErrorResponse} from '../../model/common/BadRequestErrorResponse';

interface ErrorBoxProps {
  error: BadRequestErrorResponse | null;
}

const ErrorBox: React.FC<ErrorBoxProps> = ({error}) => {
  if (!error) {
    return null;
  }

  return (
    <div style={{color: 'red', fontSize: '17px'}}>
      [{error.code}] {error.message}
    </div>
  );
};

export default ErrorBox;
