import React from 'react';
import RecommendPanel from '../commonents/recommend/RecommendPanel';
import CategoryPanel from '../commonents/recommend/CategoryPanel';

const StorePage: React.FC = () => {
  return (
    <div>
      <h4>🔥 가장 저렴한 셋트를 만나보세요!</h4>
      <div className="store-page">
        <RecommendPanel />
        <CategoryPanel />
      </div>
    </div>
  );
};

export default StorePage;
