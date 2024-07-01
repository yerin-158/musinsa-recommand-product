import React from 'react';
import './App.css';
import BrandProductsPage from './pages/BrandProductsPage';

const App: React.FC = () => {
  return (
    <div className="App">
      <header className="App-header">
        <BrandProductsPage />
      </header>
    </div>
  );
};

export default App;
