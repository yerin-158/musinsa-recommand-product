import React from 'react';
import '../../css/pagination.css';

interface PaginationProps {
  currentPage: number;
  totalPages: number;
  onPageChange: (page: number) => void;
}

const Pagination: React.FC<PaginationProps> = ({currentPage, totalPages, onPageChange}) => {
  const pages = Array.from({length: totalPages}, (_, i) => i);
  console.log('current', currentPage);
  console.log('totalPages', totalPages);
  console.log('pages', pages);

  return (
    <div className="pagination">
      <button onClick={() => onPageChange(currentPage - 1)} disabled={currentPage === 0}>
        이전
      </button>
      {pages.map((page) => (
        <button key={page} onClick={() => onPageChange(page)} className={page === currentPage ? 'active' : ''}>
          {page + 1}
        </button>
      ))}
      <button onClick={() => onPageChange(currentPage + 1)} disabled={currentPage === totalPages - 1}>
        다음
      </button>
    </div>
  );
};

export default Pagination;
