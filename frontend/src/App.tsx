import React, {useState} from 'react';
import './App.css';
import GNB from './commonents/GNB';
import StorePage from './pages/RecommendPage';
import AdminPage from './pages/AdminPage';

const App: React.FC = () => {
  const [currentTab, setCurrentTab] = useState<string>('store');

  return (
    <div className="App">
      <header className="App-header">
        <GNB currentTab={currentTab} setTab={setCurrentTab} />
        {currentTab === 'store' ? <StorePage /> : <AdminPage />}
      </header>
    </div>
  );
};

export default App;
